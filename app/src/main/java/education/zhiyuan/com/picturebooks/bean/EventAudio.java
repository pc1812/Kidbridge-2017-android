package education.zhiyuan.com.picturebooks.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Spring on 2017/8/18.
 * 音频预览
 */

public class EventAudio implements Serializable {
    public EventAudio(List<String> iconList, int id, List<Integer> sId, Map<Integer, RecordBean> mapRecoder) {
        this.iconList = iconList;
        this.id = id;
        this.sId = sId;
        this.mapRecoder = mapRecoder;
    }

    public List<String> getIconList() {
        return iconList;
    }

    public void setIconList(List<String> iconList) {
        this.iconList = iconList;
    }

    private List<String> iconList;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventAudio(int id, List<Integer> sId, Map<Integer, RecordBean> mapRecoder) {

        this.id = id;
        this.sId = sId;
        this.mapRecoder = mapRecoder;
    }



    public List<Integer> getsId() {
        return sId;
    }

    public void setsId(List<Integer> sId) {
        this.sId = sId;
    }

    public Map<Integer, RecordBean> getMapRecoder() {
        return mapRecoder;
    }

    public void setMapRecoder(Map<Integer, RecordBean> mapRecoder) {
        this.mapRecoder = mapRecoder;
    }

    private List<Integer> sId;

    private Map<Integer, RecordBean> mapRecoder;
}
