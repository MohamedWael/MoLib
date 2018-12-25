package com.blogspot.mowael.molib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blogspot.mowael.molib.R;

/**
 * Created by moham on 4/21/2017.
 */

public class MaterialEditText extends LinearLayout {
    private TextInputLayout tilInputLayout;
    private EditText editText;

    public MaterialEditText(Context context) {
        super(context);
        initView(context);
    }

    public MaterialEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setAttributeSet(context, attrs);
    }

    public MaterialEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setAttributeSet(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        setAttributeSet(context, attrs);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.material_edit_text, this);
        tilInputLayout = (TextInputLayout) findViewById(R.id.tilInputLayout);
        editText = (EditText) findViewById(R.id.editText);

    }

    private void setAttributeSet(Context context, AttributeSet attrs) {
        TypedArray styleAttributes = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);


        styleAttributes.recycle();
    }

    public EditText getEditText() {
        return editText;
    }

    public TextInputLayout getTextInputLayout() {
        return tilInputLayout;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public void setMaxLength(int maxLength) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);
    }

}
