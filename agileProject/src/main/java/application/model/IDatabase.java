package application.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class IDatabase<C>{
    
    private Map<Integer, C> data = new HashMap();
    
    
    public C getValueFromID(int id) {
        if (data.containsKey(id)){
            return data.get(id);
        }
        else {
            return null;
        }
    }
    
    public void registerValue(C c) {
        data.put(data.size(), c);
    }
    
    public Set<Entry<Integer, C>> entrySet() {
        return data.entrySet();
    }
    
    
}

