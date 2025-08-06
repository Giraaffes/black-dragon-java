package g_raffes.blackdragon.implement;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.service.ModelFactory;
import io.vavr.collection.List;

import java.io.StringReader;

public class ModelFactoryImpl implements ModelFactory<Model> {
    @Override
    public List<Model> parse(String string) {
        StringReader reader = new StringReader(string); // (closing StringReader is not necessary)
        CsvToBean<Model> beanReader = new CsvToBeanBuilder<Model>(reader)
            .withType(Model.class)
            .build();
        return List.ofAll(beanReader.parse());
    }
}
