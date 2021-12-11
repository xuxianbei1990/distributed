package calculation.chapter.base.datastructure.graph;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题2
 * 每个学生有三个属性：身份证，git账号，B站账号，有一组数据，然后确定一共几个人
 *
 * @author: xuxianbei
 * Date: 2021/12/8
 * Time: 15:26
 * Version:V1.0
 */
public class UniquePerson {

    static class Student {
        String gitAccount;
        String bAccount;
        String id;

        public Student(String gitAccount, String bAccount, String id) {
            this.gitAccount = gitAccount;
            this.bAccount = bAccount;
            this.id = id;
        }
    }


    public int execute(List<Student> list) {
        Map<String, Student> gitMap = new HashMap();
        Map<String, Student> bMap = new HashMap();
        Map<String, Student> idMap = new HashMap();
        CollectionAnd collectionAnd = new CollectionAnd();

        list.forEach(student -> {
            gitMap.put(student.gitAccount, student);
            bMap.put(student.bAccount, student);
            idMap.put(student.id, student);
        });
        collectionAnd.add(list);

        list.forEach(student -> {
            Student b = null;
            Student git = null;
            Student id = null;
            if (!StringUtils.isEmpty(student.bAccount)) {
                b = bMap.get(student.bAccount);
            }
            if (!StringUtils.isEmpty(student.gitAccount)) {
                git = gitMap.get(student.gitAccount);
            }
            if (!StringUtils.isEmpty(student.id)) {
                id = idMap.get(student.id);
            }
            collectionAnd.union(b, git);
            collectionAnd.union(b, id);
            collectionAnd.union(git, id);
        });
        return collectionAnd.size();
    }


}
