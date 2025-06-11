package imo.after_git;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import imo.after_run.TermuxUtilsV2;

public class MainActivity extends Activity 
{
	ViewGroup instruction;
	TextView outputTxt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if(! hasStoragePermission()){
            requestStoragePermission();
            finish();
            return;
		}
		
		if(! TermuxUtilsV2.permissionIsGranted(this)){
			TermuxUtilsV2.permissionRequest(this);
			finish();
			return;
		}
		
		final EditText repoPathEdit = findViewById(R.id.repo_path_edittext);
		final Button repoLoadBtn = findViewById(R.id.repo_load_btn);
		instruction = findViewById(R.id.instruction);
		outputTxt = findViewById(R.id.output_txt);
		outputTxt.setMovementMethod(new ScrollingMovementMethod());
		
		instruction.setVisibility(View.GONE);
		
		repoLoadBtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				instruction.setVisibility(View.VISIBLE);
				String repoPath = repoPathEdit.getText().toString().trim();
				String command = "cd " + repoPath;
				command += "\ngit status";
				
				TermuxUtilsV2.commandRun(command, MainActivity.this);
			}
		});
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if(! TermuxUtilsV2.commandOutputExists()) return;

		String content = TermuxUtilsV2.commandOutputRead();

		if(content.toString().trim().isEmpty()) return;

		instruction.setVisibility(View.GONE);
		outputTxt.setText(content.toString());
	}
	
	
	boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
			requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
}
