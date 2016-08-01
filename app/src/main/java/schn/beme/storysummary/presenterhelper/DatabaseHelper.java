package schn.beme.storysummary.presenterhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.mvp.chapter.Chapter;
import schn.beme.storysummary.mvp.diagram.Diagram;
import schn.beme.storysummary.mvp.scene.Scene;

/**
 * Created by Dorito on 22-07-16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "schn.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<Diagram, Integer> diagramDao;
    private Dao<Chapter, Integer> chapterDao;
    private Dao<Scene, Integer> sceneDao;



     public DatabaseHelper(Context context) {
//         super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
         super(context, DATABASE_NAME, null, DATABASE_VERSION);

     }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Diagram.class);
            TableUtils.createTable(connectionSource, Chapter.class);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            if (oldVersion < 2) {
//                dao.executeRaw("ALTER TABLE `account` ADD COLUMN age INTEGER;");
                TableUtils.createTable(connectionSource, Scene.class);

            }
            if (oldVersion < 3) //and not else if !
            {

            }

        }catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }


    public Dao<Diagram, Integer> getDiagramDao() throws SQLException {
        if (diagramDao == null) {
            diagramDao = getDao(Diagram.class);
        }
        return diagramDao ;
    }

    public Dao<Chapter, Integer> getChapterDao() throws SQLException {
        if (chapterDao == null) {
            chapterDao = getDao(Chapter.class);
        }
        return chapterDao ;
    }

    public Dao<Scene, Integer> getSceneDao() throws SQLException {
        if (sceneDao == null) {
            sceneDao = getDao(Scene.class);
        }
        return sceneDao ;
    }
}
