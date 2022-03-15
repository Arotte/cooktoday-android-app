# Useful Android code snippets

# ThemedButtonGroup Category Selection

Use the custom ThemedButtonGroup widget to create a category selection bar.


**Activity.java:**
```java
// home feed category selector
// todo: improve category selection dynamics
ThemedToggleButtonGroup themedToggleButtonGroup = findViewById(R.id.homeFeedCategories);
themedToggleButtonGroup.selectButton(R.id.homeFeedCategoryAll);
themedToggleButtonGroup.setOnSelectListener((ThemedButton btn) -> {
    // handle selected button

    // Display the currently selected buttons
    List<ThemedButton> selectedButtons = themedToggleButtonGroup.getSelectedButtons();
    StringBuilder selectedBtns = new StringBuilder("Categories: ");
    for (ThemedButton cbtn : selectedButtons) {
        selectedBtns.append(cbtn.getText());
        selectedBtns.append(", ");
    }
    selectedBtns.setLength(selectedBtns.length() - 2);
    Toast.makeText(this, selectedBtns, Toast.LENGTH_SHORT).show();

    return null;
});
```

**layout.xml:**
```xml
<nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/homeFeedCategories"
    app:justifyContent="space_between"
    app:flexWrap="wrap"
    app:toggle_requiredAmount="1"
    app:toggle_selectableAmount="1"
    app:toggle_selectAnimation="circular_reveal">

    <nl.bryanderidder.themedtogglebuttongroup.ThemedButton
        android:layout_width="wrap_content"
        android:layout_height="38dp"
        android:id="@+id/homeFeedCategoryAll"
        android:layout_margin="10dp"
        app:toggle_text="All"
        app:toggle_selectedBackgroundColor="@color/primaryGreen">
    </nl.bryanderidder.themedtogglebuttongroup.ThemedButton>

    <nl.bryanderidder.themedtogglebuttongroup.ThemedButton
        android:layout_width="wrap_content"
        android:layout_height="38dp"
        android:id="@+id/homeFeedCategory1"
        android:layout_margin="10dp"
        app:toggle_text="Vegan"
        app:toggle_selectedBackgroundColor="@color/primaryGreen">
    </nl.bryanderidder.themedtogglebuttongroup.ThemedButton>

    <nl.bryanderidder.themedtogglebuttongroup.ThemedButton
        android:layout_width="wrap_content"
        android:layout_height="38dp"
        android:id="@+id/homeFeedCategory2"
        android:layout_margin="10dp"
        app:toggle_text="Drink"
        app:toggle_selectedBackgroundColor="@color/primaryGreen">
    </nl.bryanderidder.themedtogglebuttongroup.ThemedButton>

    <nl.bryanderidder.themedtogglebuttongroup.ThemedButton
        android:layout_width="wrap_content"
        android:layout_height="38dp"
        android:id="@+id/homeFeedCategory3"
        android:layout_margin="10dp"
        app:toggle_text="Gluten Free"
        app:toggle_selectedBackgroundColor="@color/primaryGreen">
    </nl.bryanderidder.themedtogglebuttongroup.ThemedButton>

</nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup>
```

**build.gradle:**
```gradle
implementation 'nl.bryanderidder:themed-toggle-button-group:1.4.1'
```

## ThemedButtonGroup "All"

When the "All" category selection ThemedButton is pressed, select all ThemedButtons in the group.

```java
// if "All" is selected, select all
if (btn.getId() == R.id.homeFeedCategoryAll) {
    // get all unselected buttons
    List<ThemedButton> allButtons = themedToggleButtonGroup.getButtons();
    for (ThemedButton btnCurrent : allButtons) {
        if (!btnCurrent.isSelected() && btnCurrent.getId() != btn.getId()) {
            System.out.println("Selecting" + btnCurrent.getText());
            themedToggleButtonGroup.selectButton(btnCurrent.getId());
        }
    }
    Toast.makeText(this, "Selected all categories", Toast.LENGTH_SHORT).show();
    return null;
}
```

# OnTouchListener

```java
editText.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(MotionEvent.ACTION_UP == event.getAction())
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        return false;
    }

});
```

# Search bar

**xml:**
```xml
<EditText
    android:id="@+id/editTextSearchScreen"
    android:transitionName="fade"

    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="10dp"
    android:background="@drawable/custom_input_search"
    android:drawableStart="@drawable/ic_search"
    android:drawablePadding="12dp"
    android:ems="10"
    android:fontFamily="@font/interr"
    android:hint="Search"
    android:inputType="textEmailAddress"
    android:paddingStart="12dp"
    android:paddingTop="13dp"
    android:paddingEnd="12dp"
    android:paddingBottom="13dp"
    android:layout_marginTop="20dp"
    android:layout_marginLeft="20dp"
    android:layout_marginRight="20dp"
    android:focusable="false"
    />
```

**custom_input_search.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:state_enabled="true" android:state_focused="true">
        <shape android:shape="rectangle" >
            <solid android:color="@color/form"/>
            <corners android:radius="30dp" />
            <stroke android:color="@color/primaryGreen" android:width="1dp" />
        </shape>

    </item>

    <item android:state_enabled="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/form"/>
            <corners android:radius="30dp" />
        </shape>
    </item>
</selector>
```



# Upload new recipe FAB

```xml

<!--        Upload new recipe FAB -->
<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Upload"
    android:textAllCaps="false"
    app:icon="@drawable/ic_upload"
    app:iconTint= "@color/white"
    android:backgroundTint="@color/primaryGreen"
    android:textColor="@color/white"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginBottom="20dp"
    android:layout_marginEnd="20dp"
    android:stateListAnimator="@null"/>

```


# Top "bar" with welcome message and a search icon

```xml

<!--        Top bar with welcome message and search icon-->


<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/ctHomeFragmentCustomTopBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toTopOf="parent"
    tools:layout_editor_absoluteX="0dp">

    <androidx.cardview.widget.CardView
        android:id="@+id/homeFragmentTopSearchIcon"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:layout_marginEnd="20dp"
        android:backgroundTint="@color/primaryGreen"
        android:clickable="true"
        android:elevation="10dp"
        android:focusable="true"
        app:cardCornerRadius="30dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="5dp"
            android:src="@drawable/ic_search"
            app:tint="@color/white" />
    </androidx.cardview.widget.CardView>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginTop="20dp"
        android:fontFamily="@font/inter_bold"
        android:text="CookToday"
        android:textColor="@color/textMain"
        android:textSize="25sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0" />
</androidx.constraintlayout.widget.ConstraintLayout>

```

# Scale view animation

```java
public void scaleView(View v, float startScale, float endScale) {
    Animation anim = new ScaleAnimation(
            startScale, endScale, // Start and end values for the X axis scaling
            1f, 1f, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
    anim.setFillAfter(true); // Needed to keep the result of the animation
    anim.setDuration(500);
    v.startAnimation(anim);
}
```


# Icon-based top progress bar for the survey page

```java
private TopStepProgressHandler topProgressBar; // for icon pb

// ...


// for icon-based top progress bar
//    private void initStepProgressHandler() {
//        List<Integer>    iconIDs   = new ArrayList<>();
//        ConstraintLayout container = findViewById(R.id.topStepProgressContainer);
//        int              pbID      = R.id.pbStepProgress;
//
//        iconIDs.add(R.id.ivSurveyStep1);
//        iconIDs.add(R.id.ivSurveyStep2);
//        iconIDs.add(R.id.ivSurveyStep3);
//        // iconIDs.add(R.id.ivSurveyStep4);
//        // iconIDs.add(R.id.ivSurveyStep5);
//
//        this.topProgressBar = new TopStepProgressHandler(
//            NUM_PAGES,
//            iconIDs,
//            container,
//            pbID
//        );
//    }

```


```xml

    <!-- TOP STEP-BASED PROGRESS BAR WITH ICONS -->
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/topStepProgressContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="25dp"
        android:layout_marginEnd="25dp"
        android:layout_marginTop="10dp"
        android:backgroundTint="@color/transparent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:background="@color/transparent">

        <ProgressBar
            android:id="@+id/pbStepProgress"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="match_parent"
            android:layout_height="10dp"
            android:indeterminate="false"
            android:progress="55"
            android:progressBackgroundTint="#E5E5E5"
            android:progressBackgroundTintMode="src_over"
            android:progressTint="@color/primaryGreen"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageView
            android:id="@+id/ivSurveyStep1"
            android:layout_width="15dp"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_check_2"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageView
            android:id="@+id/ivSurveyStep2"
            android:layout_width="15dp"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_check_2"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/ivSurveyStep3"
            app:layout_constraintStart_toEndOf="@+id/ivSurveyStep1"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageView
            android:id="@+id/ivSurveyStep3"
            android:layout_width="15dp"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_check_2"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <!--        <ImageView-->
        <!--            android:id="@+id/ivSurveyStep4"-->
        <!--            android:layout_width="wrap_content"-->
        <!--            android:layout_height="wrap_content"-->
        <!--            android:src="@drawable/ic_check_inactive_2"-->
        <!--            app:layout_constraintBottom_toBottomOf="parent"-->
        <!--            app:layout_constraintEnd_toStartOf="@+id/ivSurveyStep5"-->
        <!--            app:layout_constraintStart_toEndOf="@+id/ivSurveyStep3"-->
        <!--            app:layout_constraintTop_toTopOf="parent" />-->

        <!--        <ImageView-->
        <!--            android:id="@+id/ivSurveyStep5"-->
        <!--            android:layout_width="wrap_content"-->
        <!--            android:layout_height="wrap_content"-->
        <!--            android:src="@drawable/ic_check_inactive_2"-->
        <!--            app:layout_constraintBottom_toBottomOf="parent"-->
        <!--            app:layout_constraintEnd_toEndOf="parent"-->
        <!--            app:layout_constraintTop_toTopOf="parent" />-->


    </androidx.constraintlayout.widget.ConstraintLayout>
    <!-- END TOP STEP-BASED PROGRESS BAR WITH ICONS -->

```


# Recipe details - user name + like count + icons

```xml

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp">

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentLeft="true"
                    android:layout_centerInParent="true"
                    android:orientation="horizontal">

                    <ImageView
                        android:id="@+id/ivRecipeDetailsAvatar"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:src="@drawable/img_sample_person1" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:layout_marginStart="7dp"
                        android:fontFamily="@font/interr"
                        android:text="Anakin Skywalker"
                        android:textColor="@color/textMain"
                        android:textSize="16sp" />
                </LinearLayout>

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentEnd="true"
                    android:layout_centerInParent="true"
                    android:layout_marginBottom="11dp"
                    android:orientation="horizontal">

                    <com.google.android.material.button.MaterialButton
                        android:id="@+id/imageButton"
                        android:layout_width="30dp"
                        android:layout_height="30dp"
                        android:layout_gravity="center"
                        android:backgroundTint="@color/primaryGreen"
                        android:insetLeft="0dp"
                        android:insetTop="0dp"
                        android:insetRight="0dp"
                        android:insetBottom="0dp"
                        app:cornerRadius="30dp"
                        app:icon="@drawable/ic_heart"
                        app:iconGravity="textStart"
                        app:iconPadding="0dp"
                        app:iconTint="@color/tp_white" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:layout_marginStart="7dp"
                        android:fontFamily="@font/interr"
                        android:text="231 Likes"
                        android:textColor="@color/textMain"
                        android:textStyle="bold" />
                </LinearLayout>
            </RelativeLayout>

```

# Alert dialog

```java
private void showAlert(String title,String message){
     AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
             .setTitle(title)
             .setMessage(message)
             .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                     // don't forget to change the line below with the names of your Activities
                     Intent intent = new Intent(SignUpActivity.this, LogoutActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                 }
             });
     AlertDialog ok = builder.create();
     ok.show();
 }

```


# Recipe Cooking Session Step Design

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="80dp"
        app:cardCornerRadius="15dp"
        app:strokeWidth="1dp"
        app:strokeColor="#D5F4E5"
        app:cardElevation="0dp">
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:translationZ="1dp"
                android:id="@+id/llCookingSessStepAndCheckContainer"
                android:layout_marginTop="10dp"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent">
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Step 1"
                    android:textColor="@color/textMain"
                    android:fontFamily="@font/interr"
                    android:textSize="20sp"
                    android:textStyle="bold"/>
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="/ 7"
                    android:fontFamily="@font/interr"
                    android:textColor="@color/textSecondary"
                    android:textSize="14sp"
                    android:layout_marginStart="7dp"/>

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:scaleType="fitEnd"
                    android:src="@drawable/ic_check_inactive" />
            </LinearLayout>

            <com.google.android.material.card.MaterialCardView
                android:layout_marginStart="10dp"
                android:layout_marginTop="6dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/llCookingSessStepAndCheckContainer"
                app:layout_constraintStart_toStartOf="parent"
                app:cardCornerRadius="15dp"
                app:strokeColor="@color/textSecondary"
                app:strokeWidth="1dp">
                <TextView
                    android:layout_marginTop="2dp"
                    android:layout_marginBottom="2dp"
                    android:layout_marginStart="5dp"
                    android:layout_marginEnd="5dp"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Preparation"
                    android:fontFamily="@font/interr"
                    android:textColor="@color/textSecondary"
                    android:textSize="11sp"/>
            </com.google.android.material.card.MaterialCardView>

            <ProgressBar
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:translationZ="0dp"
                android:progress="70"
                android:progressTint="#D5F4E5"
                android:progressBackgroundTint="@color/form"
                style="@style/Widget.AppCompat.ProgressBar.Horizontal"
                android:scaleY="40"/>
        </androidx.constraintlayout.widget.ConstraintLayout>

    </com.google.android.material.card.MaterialCardView>


    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="80dp"
        app:cardCornerRadius="15dp"
        android:backgroundTint="#D5F4E5"
        app:strokeWidth="1dp"
        app:strokeColor="#D5F4E5"
        android:layout_marginTop="15dp"
        app:cardElevation="0dp">
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:id="@+id/llCookingSessStepAndCheckContainer2"
                android:layout_marginTop="10dp"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent">
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Step 2"
                    android:textColor="@color/textMain"
                    android:fontFamily="@font/interr"
                    android:textSize="20sp"
                    android:textStyle="bold"/>
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="/ 7"
                    android:fontFamily="@font/interr"
                    android:textColor="@color/textSecondary"
                    android:textSize="14sp"
                    android:layout_marginStart="7dp"/>
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:src="@drawable/ic_check_2"
                    android:scaleType="fitEnd"/>
            </LinearLayout>

            <com.google.android.material.card.MaterialCardView
                android:layout_marginStart="10dp"
                android:layout_marginTop="6dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/llCookingSessStepAndCheckContainer2"
                app:layout_constraintStart_toStartOf="parent"
                app:cardCornerRadius="15dp"
                android:elevation="0dp"
                app:strokeColor="@color/textSecondary"
                app:strokeWidth="1dp">
                <TextView
                    android:layout_marginTop="2dp"
                    android:layout_marginBottom="2dp"
                    android:layout_marginStart="5dp"
                    android:layout_marginEnd="5dp"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Preparation"
                    android:fontFamily="@font/interr"
                    android:textColor="@color/textSecondary"
                    android:textSize="11sp"/>
            </com.google.android.material.card.MaterialCardView>
        </androidx.constraintlayout.widget.ConstraintLayout>

    </com.google.android.material.card.MaterialCardView>


    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="80dp"
        app:cardCornerRadius="15dp"
        android:layout_marginTop="15dp"
        app:cardElevation="0dp"
        app:strokeWidth="3dp"
        app:strokeColor="@color/primaryGreen">
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:id="@+id/llCookingSessStepAndCheckContainer3"
                android:layout_marginTop="10dp"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:translationZ="2dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:gravity="center_vertical"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent">
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Step 3"
                    android:textColor="@color/textMain"
                    android:fontFamily="@font/interr"
                    android:textSize="20sp"
                    android:textStyle="bold"/>
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="/ 7"
                    android:fontFamily="@font/interr"
                    android:textColor="@color/textSecondary"
                    android:textSize="14sp"
                    android:layout_marginStart="7dp"/>
                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:src="@drawable/ic_check_inactive"
                    android:scaleType="fitEnd"/>
            </LinearLayout>

            <com.google.android.material.card.MaterialCardView
                android:layout_marginStart="10dp"
                android:layout_marginTop="6dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintTop_toBottomOf="@id/llCookingSessStepAndCheckContainer3"
                app:layout_constraintStart_toStartOf="parent"
                app:cardCornerRadius="15dp"
                android:backgroundTint="@color/textMain">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="5dp"
                    android:layout_marginTop="2dp"
                    android:layout_marginEnd="5dp"
                    android:layout_marginBottom="2dp"
                    android:fontFamily="@font/interr"
                    android:text="Cooking"
                    android:textColor="@color/white"
                    android:textSize="11sp" />
            </com.google.android.material.card.MaterialCardView>

            <ProgressBar
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:translationZ="0dp"
                android:progress="30"
                android:progressTint="#D5F4E5"
                android:progressBackgroundTint="@color/form"
                style="@style/Widget.AppCompat.ProgressBar.Horizontal"
                android:scaleY="40"/>
        </androidx.constraintlayout.widget.ConstraintLayout>

    </com.google.android.material.card.MaterialCardView>
</LinearLayout>
```