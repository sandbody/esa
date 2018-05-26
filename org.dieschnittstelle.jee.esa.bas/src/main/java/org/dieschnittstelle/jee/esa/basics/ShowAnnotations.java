package org.dieschnittstelle.jee.esa.basics;


import org.dieschnittstelle.jee.esa.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.jee.esa.basics.annotations.DisplayAs;
import org.dieschnittstelle.jee.esa.basics.annotations.StockItemProxyImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static org.dieschnittstelle.jee.esa.utils.Utils.*;

public class ShowAnnotations {

    public static void main(String[] args) {
        // we initialise the collection
        StockItemCollection collection = new StockItemCollection(
                "stockitems_annotations.xml", new AnnotatedStockItemBuilder());
        // we load the contents into the collection
        collection.load();

        for (IStockItem consumable : collection.getStockItems()) {
            ;
            show("----------- Begin of pair output ------------");
            showAttributesBas2(((StockItemProxyImpl) consumable).getProxiedObject());
            showAttributesBas3(((StockItemProxyImpl) consumable).getProxiedObject());
            show("----------- End of pair output ------------");
        }

        // we initialise a consumer
        Consumer consumer = new Consumer();
        // ... and let them consume
        consumer.doShopping(collection.getStockItems());
    }

    /*
     * UE BAS2
     * Angepasst:
     */
    private static void showAttributesBas2(Object consumable) {
        Class clazz = consumable.getClass();
        StringBuilder builder = new StringBuilder();

        builder.append(clazz.getSimpleName()).append(" {\n");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(consumable);
            } catch (Exception e) {
                show("Some exception has been occurred " + e.getStackTrace());
            }

            if (null == value) {
                value = "Could not be read!.";
            }

            String attributeRepresentation = buildStringRepresentatioOfFieldValue(field, value);
            builder.append("\t").append("\"").append(field.getName()).append("\"").append(" : ").append(attributeRepresentation).append("\n");
        }

        builder.append("}");
        show(builder.toString());

    }

    /*
 * UE BAS3
 * Angepasst:
 */
    private static void showAttributesBas3(Object consumable) {
        Class clazz = consumable.getClass();
        StringBuilder builder = new StringBuilder();

        builder.append(clazz.getSimpleName()).append(" {\n");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                if (field.isAnnotationPresent(DisplayAs.class)) {
                    fieldName =field.getGenericType().getTypeName();
                }
                    value = field.get(consumable);

            } catch (Exception e) {
                show("Some exception has been occurred " + e.getStackTrace());
            }

            if (null == value) {
                value = "Could not be read!.";
            }

            String attributeRepresentation = buildStringRepresentatioOfFieldValue(field, value);
            builder.append("\t").append("\"").append(fieldName).append("\"").append(" : ").append(attributeRepresentation).append("\n");
        }

        builder.append("}");
        show(builder.toString());

    }


    /**
     * In case of JSON-Value-Presentation, check the type of the given field and descide the kind of
     * presentation then.
     *
     * @param field to check of his type
     * @param value to be presentation
     * @return String of json-attribute-value presentation
     */
    private static String buildStringRepresentatioOfFieldValue(Field field, Object value) {

        StringBuilder valBuilder = new StringBuilder();
        if (String.class.getTypeName().equals(field.getType().getTypeName())) {
            valBuilder.append("\"").append(value).append("\"");
        } else {
            valBuilder.append(value);
        }


        return valBuilder.toString();
    }


}
