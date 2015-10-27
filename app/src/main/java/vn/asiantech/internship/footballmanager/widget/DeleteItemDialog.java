package vn.asiantech.internship.footballmanager.widget;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import vn.asiantech.internship.footballmanager.R;

/**
 *  Created by sunday on 27/10/2015.
 */
public class DeleteItemDialog {
    private OnConfirmDialogListener mOnfirmDialogListener;
    public DeleteItemDialog() {
    }

    public void setOnfirmDialogListener(OnConfirmDialogListener onfirmDialogListener) {
        this.mOnfirmDialogListener = onfirmDialogListener;
    }

    public void showDialog(Context context, final int position, String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete this " + type + " ?");
        builder.setIcon(R.drawable.ic_delete_player);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mOnfirmDialogListener != null){
                    mOnfirmDialogListener.onConfirmDialog(position);
                }
            }
        });
        builder.show();
    }

    public interface OnConfirmDialogListener{
        void onConfirmDialog(int position);
    }
}
