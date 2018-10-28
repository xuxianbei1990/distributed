package search.solr;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

/**
 * User: xuxb
 * Date: 2018-10-17
 * Time: 20:01
 * Version:V1.0
 */
@Data
public class Person {

    @Field("id")
    private String id;

    @Field("name")
    private String name;

    @Field("age")
    private String age;

    @Override
    public String toString() {
        return "id:" + id + "name:" + name + "age:" + age;
    }
}
