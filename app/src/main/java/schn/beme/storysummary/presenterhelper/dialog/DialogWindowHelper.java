package schn.beme.storysummary.presenterhelper.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.presenterhelper.Helper;

/**
 * Created by Dorito on 19-07-16.
 */
public final class DialogWindowHelper implements Helper.DialogWindow{


    //-----------SINGLETON HOLDER METHODOLOGY---------------

    private DialogWindowHelper(){}

    public static DialogWindowHelper getInstance(){return DialogWindowHolder.instance; }

    private static class DialogWindowHolder{
        private final static DialogWindowHelper instance= new DialogWindowHelper();
    }


    //----------------WORKING METHODS---------------

    @Override
    public void showConfirm(Context actContext, String title, String msg, final ConfirmDialogListener listener) {
        //I don't know why but it does not work anymore with application context :(
        new AlertDialog.Builder(actContext)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        listener.accepted();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        listener.canceled();
                    }
                }).show();
    }


    @Override
    public void showConfirmEditText(Context actContext,String title, String msg,final boolean empty, final ConfirmEditDialogListener listener)
    {
        final EditText edit= new EditText(MyApplication.getAppContext());
        edit.setMaxLines(1);
        edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        new AlertDialog.Builder(actContext)
                .setTitle(title)
                .setMessage(msg)
                .setView(edit)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!empty&&(edit.getText().toString().equals(""))) {
                            Toast.makeText(MyApplication.getAppContext(), "Can not be empty", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            dialog.cancel();
                            listener.accepted(edit.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        listener.canceled();
                    }
                }).show();
    }



}
