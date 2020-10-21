package com.example.testcloudfirebase.ViewHolder;

public interface OnItemClickListener{
    void onAddClick(String idDoc);
    void onDeleteClick(String idDay,String idDoc,String idProd);
    void OnAddProd(String idProduct);
}
