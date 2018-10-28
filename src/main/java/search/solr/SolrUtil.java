package search.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

/**
 * User: xuxb
 * Date: 2018-10-17
 * Time: 20:07
 * Version:V1.0
 */
public class SolrUtil {

    public static final String SOLRURL = "192.168.2.2:8983/solr";

    /**
     * * 该对象有两个可以使用，都是线程安全的
     * 1、CommonsHttpSolrServer：启动web服务器使用的，通过http请求的
     * 2、 EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     *
     * @return
     */
    public HttpSolrClient createSolrServer() {
        HttpSolrClient result = null;
        result = new HttpSolrClient.Builder(SOLRURL).withConnectionTimeout(10000).withSocketTimeout(60000).build();
        return result;
    }

    /*
    往索引添加文档
     */
    public void addDoc() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "8");
        document.addField("name", "简单");
        document.addField("description", "测试样例");
        HttpSolrClient solr = getHttpSolrClient();
        solr.add(document);
        solr.commit();
        solr.close();
    }

    private HttpSolrClient getHttpSolrClient() {
        return new HttpSolrClient.Builder(SOLRURL + "my_core").withConnectionTimeout(10000).withSocketTimeout(60000).build();
    }

    public void deleteDocumentById() throws IOException, SolrServerException {
        HttpSolrClient client = getHttpSolrClient();
        client.deleteById("8");
        client.commit();
        client.close();
    }

    public void querySolr() throws IOException, SolrServerException {
        HttpSolrClient client = getHttpSolrClient();
        SolrQuery query = new SolrQuery();

        //query.set("q", "*:*");// 参数q  查询所有
        query.set("q", "简单");
        //参数fq, 给query增加过滤查询条件
        query.addFilterQuery("id:[0 TO 9]");
        //参数df,给query设置默认搜索域
        query.set("df", "name");
        //参数sort,设置返回结果的排序规则
        query.setSort("id", SolrQuery.ORDER.desc);
        //设置分页参数
        query.setStart(0);
        query.setRows(10);//每一页多少值

        //参数hl,设置高亮
        query.setHighlight(true);
        //设置高亮的字段
        query.addHighlightField("name");
        //设置高亮的样式
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        QueryResponse response = client.query(query);
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("通过文档获取查询的结果");
        System.out.println("数量:" + solrDocumentList.getNumFound());

        for (SolrDocument doc : solrDocumentList) {
            System.out.println("id:" + doc.get("id") + ":name:" + doc.get("name") + ":description:" +
                    doc.get("description"));
        }

        List<Person> tmpLists = response.getBeans(Person.class);
        if (tmpLists != null && tmpLists.size() > 0) {
            System.out.println("查询的结果");
            for (Person person: tmpLists) {
                System.out.println(person.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException, SolrServerException {
        SolrUtil solrUtil = new SolrUtil();
        solrUtil.addDoc();
        solrUtil.deleteDocumentById();
        solrUtil.querySolr();
    }
}
