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



        <LinearLayout
            android:id="@+id/layoutSurveyStep2BtnContainer"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginStart="10dp"
            android:layout_marginEnd="10dp"
            android:layout_marginBottom="10dp"
            app:layout_constraintBottom_toBottomOf="parent">

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnBackSurveyStep2"
                android:layout_width="match_parent"
                android:layout_height="70dp"
                android:backgroundTint="@color/white"
                android:fontFamily="@font/interr"
                android:text="Previous"
                android:layout_weight="1"
                android:textAllCaps="false"
                android:textColor="@color/textSecondary"
                android:textSize="16sp"
                android:textStyle="bold"
                app:cornerRadius="30dp"
                android:layout_margin="10dp"
                app:rippleColor="@color/textSecondary"
                app:strokeColor="@color/textSecondary"
                app:strokeWidth="2dp" />

            <com.google.android.material.button.MaterialButton
                android:layout_margin="10dp"
                android:id="@+id/btnNextSurveyStep2"
                android:layout_width="match_parent"
                android:layout_height="70dp"
                android:layout_weight="1"
                android:backgroundTint="@color/primaryGreen"
                android:fontFamily="@font/interr"
                android:text="Next"
                android:textAllCaps="false"
                android:textColor="@color/white"
                android:textSize="16sp"
                android:textStyle="bold"
                app:cornerRadius="30dp"
                app:layout_constraintBottom_toBottomOf="parent"
                tools:layout_editor_absoluteX="30dp" />
        </LinearLayout>



                <com.google.android.material.button.MaterialButton
                    android:id="@+id/btnNextSurveyStep1"
                    android:layout_width="match_parent"
                    android:layout_height="70dp"
                    android:backgroundTint="@color/primaryGreen"
                    android:fontFamily="@font/interr"
                    android:text="Next"
                    android:textAllCaps="false"
                    android:textColor="@color/white"
                    android:textSize="16sp"
                    android:textStyle="bold"
                    app:cornerRadius="30dp"
                    app:layout_constraintBottom_toBottomOf="parent"
                    tools:layout_editor_absoluteX="30dp"
                    android:layout_marginStart="30dp"
                    android:layout_marginEnd="30dp"
                    android:layout_marginBottom="20dp"/>