package com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.view.IDetailGroupWorkMainView;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.converStringToDateTime;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isDateInCurrentWeek;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isToday;

/**
 * Created by lxtri on 11/21/2017.
 */

public class DetailGroupWorkMainPresenter implements IDetailGroupWorkMainPresenter,GetGroupWorkListener,GetWorkListener{

    IDetailGroupWorkMainView iDetailGroupWorkMainView;
    ArrayList<NhomCVModel> mListNhomCV;
    ArrayList<CongViecModel> mListCV;
    DBGroupWorkServer dbGroupWorkServer;
    DBWorkServer dbWorkServer;
    int idnguoidung;
    String ID_SCREEN="";

    public DetailGroupWorkMainPresenter(IDetailGroupWorkMainView iDetailGroupWorkMainView){
        this.iDetailGroupWorkMainView = iDetailGroupWorkMainView; }

    public ArrayList<NhomCVModel> getListNhomCV(){
        return mListNhomCV;}

    @Override
    public void getAllWorkOnline(Integer idnguoidung, String ID_SCREEN) {
        mListNhomCV = new ArrayList<NhomCVModel>();
        mListCV = new ArrayList<CongViecModel>();

        iDetailGroupWorkMainView.showLoading();
        dbGroupWorkServer = new DBGroupWorkServer(this);
        dbGroupWorkServer.getListGroupWork(idnguoidung);
        this.idnguoidung = idnguoidung;
        this.ID_SCREEN = ID_SCREEN;
    }

    public ArrayList<NhomCVModel> setupListNhomCVAssigned(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                       ArrayList<CongViecModel> congViecModelArrayList){
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom())){
                    if(congViecModelArrayList.get(j).getIdNguoiDuocGiaoCV()!=null){
                        if(congViecModelArrayList.get(j).getIdNguoiDuocGiaoCV() == idnguoidung)
                            listCVTemp.add(congViecModelArrayList.get(j));
                    }
                }
            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;
    }

    public ArrayList<NhomCVModel> setupListNhomCVStarred(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                                         ArrayList<CongViecModel> congViecModelArrayList){
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom()) &&
                        congViecModelArrayList.get(j).getCoUuTien().equals(1)){
                    listCVTemp.add(congViecModelArrayList.get(j));
                }
            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;
    }

    public ArrayList<NhomCVModel> setupListNhomCVToday(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                                         ArrayList<CongViecModel> congViecModelArrayList) throws ParseException {
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom())){
                    if(congViecModelArrayList.get(j).getThoiGianBatDau()!=null){
                        if(isToday(converStringToDateTime(congViecModelArrayList.get(j).getThoiGianBatDau())))
                         listCVTemp.add(congViecModelArrayList.get(j));
                    }
                }
            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;
    }

    public ArrayList<NhomCVModel> setupListNhomCVWeek(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                                         ArrayList<CongViecModel> congViecModelArrayList) throws ParseException {
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom())){
                    if(congViecModelArrayList.get(j).getThoiGianBatDau()!=null){
                        if (isDateInCurrentWeek(converStringToDateTime(congViecModelArrayList.get(j).getThoiGianBatDau()))){
                            listCVTemp.add(congViecModelArrayList.get(j));
                        }
                    }
                }

            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;
    }

    public ArrayList<NhomCVModel> setupListNhomCVAll(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                                         ArrayList<CongViecModel> congViecModelArrayList){
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom())){
                    listCVTemp.add(congViecModelArrayList.get(j));
                }
            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;

    }

    public ArrayList<NhomCVModel> setupListNhomCVCompleted(ArrayList<NhomCVModel> nhomCVModelArrayList,
                                                     ArrayList<CongViecModel> congViecModelArrayList){
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<congViecModelArrayList.size();j++){
                if(nhomCVModelArrayList.get(i).getIdNhom().equals(congViecModelArrayList.get(j).getIdNhom()) &&
                       congViecModelArrayList.get(j).getTrangThai().equals("done")){
                    listCVTemp.add(congViecModelArrayList.get(j));
                }
            }
            nhomCVModelArrayList.get(i).setCongViecModels(listCVTemp);
        }
        return nhomCVModelArrayList;
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListCV = congViecModelArrayList;
        switch (ID_SCREEN){
            case "1":
                mListNhomCV = setupListNhomCVAssigned(mListNhomCV,mListCV);
                break;
            case "2":
                mListNhomCV = setupListNhomCVStarred(mListNhomCV,mListCV);
                break;
            case "3":
                try {
                    mListNhomCV = setupListNhomCVToday(mListNhomCV,mListCV);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "4":
                try {
                    mListNhomCV = setupListNhomCVWeek(mListNhomCV,mListCV);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "5":
                mListNhomCV = setupListNhomCVAll(mListNhomCV,mListCV);
                break;
            case "6":
                mListNhomCV = setupListNhomCVCompleted(mListNhomCV,mListCV);
                break;
        }
        iDetailGroupWorkMainView.hideLoading();
        iDetailGroupWorkMainView.getDetailGroupWorkSuccess();
    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {
        if(listGroupWork==null){
            iDetailGroupWorkMainView.hideLoading();
            iDetailGroupWorkMainView.getDetailGroupWorkFail();
        }else{
            NhomCVModel inbox = new NhomCVModel();
            inbox.setTenNhom("Inbox");
            inbox.setIdNhom(0);
            mListNhomCV.add(inbox);
            mListNhomCV.addAll(listGroupWork);
            dbWorkServer = new DBWorkServer(this);
            dbWorkServer.getListAllWork(idnguoidung);
        }
    }
}
