package kozitski.data.converter.io.writer;

import kozitski.data.converter.io.IOConstant;
import kozitski.data.converter.io.reader.AbstractCsvReader;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Abstract avro writer.
 *
 * @param <T> the type parameter
 */
@Slf4j
public abstract class AbstractAvroWriter<T> {
    @Setter
    private String avroFilePath = IOConstant.TEST_WRITE_PATH;

    /**
     * Define schema schema.
     *
     * @return the schema
     */
    public abstract Schema defineSchema();

    /**
     * Define csv reader abstract csv reader.
     *
     * @return the abstract csv reader
     */
    public abstract AbstractCsvReader<T> defineCsvReader();

    /**
     * Write record.
     *
     * @param record  the record
     * @param element the element
     */
    public abstract void writeRecord(GenericData.Record record, T element);

    /**
     * Write record.
     */
    @SuppressWarnings("unchecked")
    public void writeRecord(){
        Schema schema = defineSchema();
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);

        Configuration conf = new Configuration();
        try (
                FileSystem fs = FileSystem.get(URI.create(avroFilePath), conf);
                OutputStream out = fs.create(new Path(avroFilePath))
        ){

            AbstractCsvReader<T> csvReader = defineCsvReader();
            while (csvReader.isHasMore()){
                List<T> testDTOS = csvReader.readPart();

                List<GenericRecord> tests = new LinkedList<>();
                testDTOS.forEach(element -> {
                    if(element != null){
                        GenericData.Record record = new GenericData.Record(schema);

                        writeRecord(record, element);

                        tests.add(record);
                    }
                });

                try(DataFileWriter dataFileWriter = new DataFileWriter<>(datumWriter)) {
                    dataFileWriter.create(schema, out);

                    for (GenericRecord record: tests) {
                        dataFileWriter.append(record);
                        log.info("....record was appended");
                    }
                }

            }

        }
        catch (IOException e) {
            log.error("Exception during AVRO-file creating", e);
        }
    }

}

