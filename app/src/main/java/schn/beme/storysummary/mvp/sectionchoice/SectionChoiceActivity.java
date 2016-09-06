package schn.beme.storysummary.mvp.sectionchoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.MyApplication;
import schn.beme.storysummary.mvp.defaults.DefaultActivity;
import schn.beme.storysummary.mvp.diagram.DiagramActivity;
import schn.beme.storysummary.narrativecomponent.Diagram;

public class SectionChoiceActivity extends DefaultActivity implements SectionChoicePresenter.View{

    private SectionChoicePresenter presenter;
    //TODO avec picasso tu peux mettre les images en cache, réduisant la mémoire
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter=new SectionChoicePresenter<SectionChoicePresenter.View>(this);
        setContentView(R.layout.activity_section_choice);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        presenter.viewInitialized();
    }

    @Override
    public void onBackPressed()//by default it recreates de last activity
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public void onClickSectionImage(View v)
    {
        if(v.getId()==R.id.diagram_section_img)
        {
          presenter.sectionDiagramSelected();
        }
    }



}
