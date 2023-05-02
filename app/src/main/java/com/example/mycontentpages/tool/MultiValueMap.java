package com.example.mycontentpages.tool;

import java.util.*;
public class MultiValueMap {
    private Map<Object, Object> map = new HashMap();

  public void put(Object key){
    if(map.get(key)==null){
        List<Object> list=new ArrayList<>();
        map.put(key,list);
    }
}
    public void put(Object key, Object value) {
        if (map.get(key) == null) {
            List<Object> list = new ArrayList<>();
            list.add(value);
            map.put(key,list);
        } else {
            ((List) map.get(key)).add(value);
    }
  }
    public  Object get(Object key, Class clazz) {
      if(map.get(key)==null){return null;}
        for (Object object : (List) map.get(key)) {
            if (object.getClass() == clazz) {
                return object;
            }
        }
        return null;
    }
    public List get(Object key){

     List list=(List) map.get(key);
     if(list!=null)
     {return list;}
     else{return new ArrayList<>();}
    }

    public void remove(Object key){
      map.remove(key);
    }
    public void remove(Object key,Class clazz){
      for(Object object:(List)map.get(key)){
          if(object.getClass()==clazz){
              ((List) map.get(key)).remove(object);
          }
      }
    }

    public int getSize(){
      return map.size();
    }
    public int getSize(Object key){
      if(map.get(key)!=null){
      return ((List)map.get(key)).size();}
      else {
          return 0;
      }
    }

    public void put(Object key,Object...objects){
      for(Object object:objects){
       put(key,object);
      }
    }

    public List getList(Object key,Class clazz){
      if(map.get(key)==null){
          return null;
      }
      List list=new ArrayList();
         for(Object object:(List)map.get(key)){
             if(object.getClass()==clazz){
                 list.add(object);
             }
         }
      return list;
    }

    public Set getKeyList(){
      return map.keySet();
    }
}
