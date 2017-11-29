/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UzytkownikDao;
import data.UzytkownikTo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Windows
 */
@ManagedBean
@SessionScoped
public class SessionController {

    List<UzytkownikTo> uzytkownikToList = new ArrayList();
    public SessionController() {
        refreshData();
    }

    public List<UzytkownikTo> getUzytkownikToList() {
        return this.uzytkownikToList;
    }

    public void setUzytkownikToList(List<UzytkownikTo> uzytkownikToList) {
        this.uzytkownikToList = uzytkownikToList;
    }
    public void visibleChange (UzytkownikTo uzytkownikTo){
        int indexObject = uzytkownikToList.indexOf(uzytkownikTo);
        UzytkownikDao daneDao = new UzytkownikDao();
        if(daneDao.update(uzytkownikTo)!=-1){
            uzytkownikToList.set(indexObject,uzytkownikTo);
        }
    }
    public void deleteRow (UzytkownikTo uzytkownikTo){
        int indexObject = uzytkownikToList.indexOf(uzytkownikTo);
        UzytkownikDao daneDao = new UzytkownikDao();
        if(daneDao.delete(uzytkownikTo.getId())!=-1){
            uzytkownikToList.remove(indexObject);
        }
    }
    public void addRow (UzytkownikTo uzytkownikTo){
        int indexObject = uzytkownikToList.indexOf(uzytkownikTo);
        int listSize = uzytkownikToList.size();
        UzytkownikTo newRow = new UzytkownikTo(-1l,"","",true);
        UzytkownikDao daneDao = new UzytkownikDao();
        Long id = daneDao.create(newRow);
        if(id != null){
            newRow.setId(id);
            uzytkownikToList.add(indexObject+1,newRow);
        }
    }
    
    public void sort(String dir){
        Comparator<UzytkownikTo> comparator = (UzytkownikTo a , UzytkownikTo b) ->{
            int compareResult = 0;
            if(dir.equals("asc")){
                compareResult = a.getNazwisko().compareTo(b.getNazwisko());
            }
            else{
                compareResult = b.getNazwisko().compareTo(a.getNazwisko());
            }
            return compareResult;
        };
                Collections.sort(uzytkownikToList,comparator);
    }
    
    public final void refreshData(){
        UzytkownikDao daneDao = new UzytkownikDao();
        List<UzytkownikTo> uzytkownikToListLocal = daneDao.findAll();
        if(uzytkownikToListLocal != null){
            uzytkownikToList.clear();
            uzytkownikToList = uzytkownikToListLocal;
        }
    }
    
    
}
