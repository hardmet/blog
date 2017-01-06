package ru.breathoffreedom.mvc.util;

import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by boris_azanov on 29.12.16.
 */
public class ModelBuilder extends ModelAndView implements Iterable {

    public ModelBuilder() {
    }

    public ModelBuilder(String view) {
        super(view);
    }


    public void put(String name, Object object) {
        this.addObject(name, object);
    }

    public ModelBuilder createCollection(String collectionName) {
        ModelBuilder newModel = new ModelBuilder();
        this.put(collectionName, newModel);
        return newModel;
    }

    public ModelBuilder get(String modelName) {
        Map<String, Object> mod = this.getModel();
        for (String name: mod.keySet()) {
            if (name.equals(modelName)) {
                return (ModelBuilder) mod.get(name);
            }
        }
        return (ModelBuilder) new Object();
    }

    @Override
    public Iterator iterator() {
        Collection models = this.getModelMap().values();
        return models.iterator();
    }

}
