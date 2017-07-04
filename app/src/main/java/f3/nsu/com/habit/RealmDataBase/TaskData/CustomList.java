package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/3.
 * 自定义列表
 */


public class CustomList extends RealmObject {
    @PrimaryKey
    private String name;     //名称

    private int day;         //坚持的天数
    private String word;     //一段鼓励的话
    private String color;   //预设计的颜色

    public CustomList() {
    }

    public CustomList(String name, int day, String word, String color) {
        this.name = name;
        this.day = day;
        this.word = word;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
