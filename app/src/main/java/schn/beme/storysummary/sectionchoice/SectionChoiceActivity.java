package schn.beme.storysummary.sectionchoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.diagram.DiagramActivity;

public class SectionChoiceActivity extends AppCompatActivity implements SectionChoicePresenter.View{

    private SectionChoicePresenter presenter;
    //TODO avec picasso tu peux mettre les images en cache, réduisant la mémoire
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_choice);
        presenter=new SectionChoicePresenter(this);
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
            Intent intent = new Intent(this, DiagramActivity.class);
            startActivity(intent);
        }
    }



}
