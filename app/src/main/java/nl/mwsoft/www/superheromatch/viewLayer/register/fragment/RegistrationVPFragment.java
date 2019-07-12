package nl.mwsoft.www.superheromatch.viewLayer.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.adapter.RegisterViewPagerAdapter;

public class RegistrationVPFragment extends Fragment {

    @BindView(R.id.vpRegister)
    ViewPager vpRegister;
    private RegisterViewPagerAdapter registerViewPagerAdapter;
    @BindView(R.id.llDots)
    LinearLayout llDots;
    private ImageView[] dots;
    @BindView(R.id.btnPrevious)
    Button btnPrevious;
    @BindView(R.id.btnContinue) Button btnContinue;
    private Unbinder unbinder;
    private int currentPosition = 0;


    private RegisterActivity registerActivity;

    public static RegistrationVPFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationVPFragment fragment = new RegistrationVPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_vp, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadRegistrationViewPager();
    }

    private void loadRegistrationViewPager() {
        configureViewPager();
        showDots(0);
        vpRegister.addOnPageChangeListener(myOnPageChangeListener);
    }

    private void configureViewPager() {
        registerViewPagerAdapter = new RegisterViewPagerAdapter(getChildFragmentManager());
        vpRegister.setOffscreenPageLimit(0);
        vpRegister.setAdapter(registerViewPagerAdapter);
        vpRegister.setOnTouchListener(myOnTouchListener);
    }

    private void showDots(int currentPosition){
        resetDots();
        showCurrentDots(currentPosition);
    }

    private void showCurrentDots(int currentPosition) {
        dots = new ImageView[6];

        for(int i = 0; i < 6; i++){
            dots[i] = new ImageView(registerActivity);
            if(i == currentPosition){
                dots[i].setImageDrawable(ContextCompat.getDrawable(registerActivity, R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(registerActivity,R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            llDots.addView(dots[i],params);
        }
    }

    private void resetDots() {
        if(llDots != null){
            llDots.removeAllViews();
        }
    }

    private void runOnPageSelectedRoutine(int position) {
        currentPosition = position;
        if(currentPosition > 0){
            btnPrevious.setVisibility(View.VISIBLE);
        }else if(currentPosition == 0){
            btnPrevious.setVisibility(View.GONE);
        }
        showDots(position);
    }

    public ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            runOnPageSelectedRoutine(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    public ViewPager.OnTouchListener myOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    @OnClick(R.id.btnPrevious)
    public void  previousClickListener() {
        if(currentPosition >= 1){
            currentPosition--;
            vpRegister.setCurrentItem(currentPosition);
        }
    }

    @OnClick(R.id.btnContinue)
    public void  continueClickListener() {
        if(currentPosition < 5){
             if(currentPosition == 0){
                if(registerActivity.processSuperheroNameContinue()){
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }
            }else if(currentPosition == 1){
                if(registerActivity.processSuperheroBirthdayContinue()){
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }
            }else if(currentPosition == 2){
                if(registerActivity.processSuperheroGenderContinue()){
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }
            }else if(currentPosition == 3){
                if(registerActivity.processSuperheroLookingForGenderContinue()){
                    currentPosition++;
                    vpRegister.setCurrentItem(currentPosition);
                }
            } else if(currentPosition == 4){
                 if(registerActivity.processSuperPowerContinue()){
                     currentPosition++;
                     vpRegister.setCurrentItem(currentPosition);
                 }
             }
        }else if(currentPosition == 5){
            if(registerActivity.processProfilePicContinue()){
                registerActivity.register(registerActivity.convertToJSON(registerActivity.getUser()));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerActivity = (RegisterActivity)context;
    }

}

