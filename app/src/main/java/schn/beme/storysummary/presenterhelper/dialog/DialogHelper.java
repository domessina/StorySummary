package schn.beme.storysummary.presenterhelper.dialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 19-07-16.
 */
public final class DialogHelper {

    public static void showConfirm(String title, String msg, final ConfirmDialogListener listener) {
        new AlertDialog.Builder(MyApplication.getCrntActivityContext())
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


    public static void showConfirmEditText(String title, String msg,final boolean empty, final ConfirmEditDialogListener listener)
    {
        final EditText edit= new EditText(MyApplication.getCrntActivityContext());
        edit.setMaxLines(1);
        new AlertDialog.Builder(MyApplication.getCrntActivityContext())
                .setTitle(title)
                .setMessage(msg)
                .setView(edit)
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!empty&&(edit.getText().toString().equals(""))) {
                            Toast.makeText(MyApplication.getCrntActivityContext(), "Can not be empty", Toast.LENGTH_LONG).show();
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
