package schn.beme.storysummary.presenterhelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import schn.beme.storysummary.narrativecomponent.Chapter;
import schn.beme.storysummary.narrativecomponent.Diagram;
import schn.beme.storysummary.narrativecomponent.Scene;
import schn.beme.storysummary.narrativecomponent.Trait;
import schn.beme.storysummary.narrativecomponent.Character;
import schn.beme.storysummary.presenterhelper.Helper;

/**
 * Created by Dorito on 22-07-16.
 */
public class OrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper implements Helper.Database {

    private static final String DATABASE_NAME = "schn.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Diagram, Integer> diagramDao;
    private Dao<Chapter, Integer> chapterDao;
    private Dao<Scene, Integer> sceneDao;
    private Dao<Character, Integer> characterDao;
    private Dao<Trait, Integer> traitDao;



     public OrmLiteDatabaseHelper(Context context) {
//         super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
         super(context, DATABASE_NAME, null, DATABASE_VERSION);

     }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            //for ON DELETE CASCADE before creating tables
            database.setForeignKeyConstraintsEnabled(true);

            TableUtils.createTable(connectionSource, Diagram.class);
            TableUtils.createTable(connectionSource, Chapter.class);
            TableUtils.createTable(connectionSource, Scene.class);
            TableUtils.createTable(connectionSource, Character.class);
            TableUtils.createTable(connectionSource, Trait.class);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

/*        try {
            if (oldVersion < 2) {
//                dao.executeRaw("ALTER TABLE `account` ADD COLUMN age INTEGER;");

            }
            if (oldVersion < 4) //and not else if !
            {

            }

        }catch (SQLException e) {
            Log.e(OrmLiteDatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }*/
    }


    @Override
    public Dao<Diagram, Integer> getDiagramDao()  {
        if (diagramDao == null) {

            try{diagramDao = getDao(Diagram.class);}
            catch (SQLException e){e.printStackTrace();}
        }
        return diagramDao ;
    }

    @Override
    public Dao<Chapter, Integer> getChapterDao()  {
        if (chapterDao == null) {

            try{chapterDao = getDao(Chapter.class);}
            catch (SQLException e){e.printStackTrace();}
        }
        return chapterDao ;
    }

    @Override
    public Dao<Scene, Integer> getSceneDao()  {
        if (sceneDao == null) {

            try{ sceneDao = getDao(Scene.class);}
            catch (SQLException e){e.printStackTrace();}
        }
        return sceneDao ;
    }

    @Override
    public Dao<Character,Integer> getCharacterDao(){
        if(characterDao == null){
            try {
                characterDao=getDao(Character.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return characterDao;
    }
    @Override
    public Dao<Trait, Integer> getTraitDao()  {
        if (traitDao == null) {

            try{ traitDao = getDao(Trait.class);}
            catch (SQLException e){e.printStackTrace();}
        }
        return traitDao;
    }
}
