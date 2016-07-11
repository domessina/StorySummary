package schn.beme.storysummary.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import schn.beme.be.storysummary.R;
import schn.beme.storysummary.DefaultActivity;
import schn.beme.storysummary.sectionchoice.SectionChoiceActivity;

public class RegistrationActivity extends DefaultActivity implements RegistrationPresenter.View{

    private RegistrationPresenter presenter;
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        presenter=new RegistrationPresenter(this);
        sliderLayout.startAutoCycle();
    }

    private void initViews()
    {
        initImageSlider();
    }

    private void initImageSlider()
    {
        sliderLayout= (SliderLayout)findViewById(R.id.image_slider);
        sliderLayout.setCustomIndicator((PagerIndicator)findViewById(R.id.slider_indicator));
        DefaultSliderView slider1=new DefaultSliderView(this);
        slider1.image(R.drawable.spring);
        sliderLayout.addSlider(slider1);
        DefaultSliderView slider2= new DefaultSliderView(this);
        slider2.image(R.drawable.j2uy5ajo);
        sliderLayout.addSlider(slider2);
        DefaultSliderView slider3= new DefaultSliderView(this);
        slider3.image(R.drawable.w1g35p08);
        sliderLayout.addSlider(slider3);
    }


    @Override
    protected void onPause()
    {
        sliderLayout.stopAutoCycle();
        super.onPause();
    }

    @Override       //relacher des resssources , =null, etc
    protected void onStop()
    {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void onClickFbButton(View v)
    {
        presenter.fbBtnClicked();
    }



}
