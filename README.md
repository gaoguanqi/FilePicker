# FilePicker
lib

# How to
To get a Git project into your build:

# Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
# Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.gaoguanqi:FilePicker:1.0.0'
	}
Share this release:

# 使用方法：
# 1，  Theme  NoTitleBar
# 2，  权限申请 READ_EXTERNAL_STORAGE
# 3， FilePicker.from(this)
                .chooseForBrowser()
                .setMaxCount(1)
                .setFileTypes("png", "doc", "apk", "mp3", "gif", "txt", "mp4", "zip")
                .requestCode(REQUEST_CODE_FILE)
                .start();

# 4，  @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
                    ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                    if (essFileList != null && !essFileList.isEmpty()) {
                        String path = essFileList.get(0).getAbsolutePath();
                        tvContent.setText(path);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    } 

  
