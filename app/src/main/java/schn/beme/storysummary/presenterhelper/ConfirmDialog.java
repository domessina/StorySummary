package schn.beme.storysummary.presenterhelper;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import schn.beme.storysummary.MyApplication;

/**
 * Created by Dorito on 19-07-16.
 */
public final class ConfirmDialog {

    public static void show(final ConfirmListener listener) {

        new AlertDialog.Builder(MyApplication.getCurntActivityContext())
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        listener.accepted();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        listener.canceled();
                    }
                })
                .show();
    }


}
