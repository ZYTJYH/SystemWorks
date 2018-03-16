package zytjyh.com.hellocharts;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ZYTJYH on 15/3/2018.
 */

public class ShowItem {
    private String name;
    private boolean isExplode;
    private String goWhich;
    private List<String> storage;

    public ShowItem(String name, boolean isExplode, String goWhich, List<String> storage) {
        this.name = name;
        this.isExplode = isExplode;
        this.goWhich = goWhich;
        this.storage = storage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExplode() {
        return isExplode;
    }

    public void setExplode(boolean explode) {
        isExplode = explode;
    }

    public String getGoWhich() {
        return goWhich;
    }

    public void setGoWhich(String goWhich) {
        this.goWhich = goWhich;
    }

    public List<String> getStorage() {
        return storage;
    }

    public void setStorage(List<String> storage) {
        this.storage = storage;
    }
}
