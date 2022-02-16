package com.aron.cooktoday.upload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class GetContentWithChooser extends ActivityResultContract<Pair<String, String>, Uri> {

    @NonNull
    @NotNull
    @Override public Intent createIntent(@NonNull @NotNull Context context, Pair<String, String> input) {
        Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getContentIntent.setType(input.first);
        getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType(input.first);
        Intent chooserIntent = Intent.createChooser(getContentIntent, input.second);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        return chooserIntent;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable @Override public SynchronousResult<Uri> getSynchronousResult(@NonNull @NotNull Context context, Pair<String, String> input) {
        return super.getSynchronousResult(context, input);
    }

    @Nullable
    @Override
    public final Uri parseResult(int resultCode, @Nullable Intent intent) {
        if (intent == null || resultCode != Activity.RESULT_OK) return null;
        return intent.getData();
    }
}