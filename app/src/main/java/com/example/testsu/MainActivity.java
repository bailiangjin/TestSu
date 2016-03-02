package com.example.testsu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.stericson.RootTools.RootTools;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// shell 编程基础
		// RootTools.installBinary(this, R.raw.key, "key");
		// RootTools.sendShell("运行 key ", timeout);
		// try {
		// String cmd = "cd data/data/com.example.testsu/files" + "\n"
		// + "./key";
		// RootTools.sendShell(cmd, 30000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// RootTools.
	}

	public void click1(View view) {
		try {
			// List<String> results = RootTools.sendShell(
			// "pm disable com.qihoo360.mobilesafe", 30000);
			// System.out.println(results.get(0));
			// List<String> results1 = RootTools.sendShell(
			// "pm disable eu.chainfire.supersu", 30000);
			// System.out.println(results1.get(0));
			// List<String> results2 = RootTools.sendShell(
			// "pm disable com.noshufou.android.su", 30000);
			// System.out.println(results2.get(0));
			// List<String> results3 = RootTools.sendShell(
			// "pm disable co.lvdou.superuser", 30000);
			// System.out.println(results3.get(0));
			// List<String> results4 = RootTools.sendShell(
			// "pm disable com.mgyun.shua.su", 30000);
			// System.out.println(results4.get(0));
			RootUtils.excuteRootCMD("pm disable com.noshufou.android.su");
			RootUtils.excuteRootCMD("pm disable eu.chainfire.supersu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void click2(View view) {
		try {
			// List<String> results = RootTools.sendShell(
			// "pm enable com.qihoo360.mobilesafe", 30000);
			// System.out.println(results.get(0));
			// List<String> results1 = RootTools.sendShell(
			// "pm enable eu.chainfire.supersu", 30000);
			// System.out.println(results1.get(0));
			// List<String> results2 = RootTools.sendShell(
			// "pm enable com.noshufou.android.su", 30000);
			// System.out.println(results2.get(0));
			// List<String> results3 = RootTools.sendShell(
			// "pm enable co.lvdou.superuser", 30000);
			// System.out.println(results3.get(0));
			// List<String> results4 = RootTools.sendShell(
			// "pm enable com.mgyun.shua.su", 30000);
			// System.out.println(results4.get(0));
			RootUtils.excuteRootCMD("pm enable com.noshufou.android.su");
			RootUtils.excuteRootCMD("pm enable eu.chainfire.supersu");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void click3(View view) {
		try {
			RootUtils.excuteRootCMD("pm disable com.lbe.security");
			List<String> results = RootTools.sendShell(
					"pm disable com.lbe.security", 30000);
			System.out.println(results.get(0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void click4(View view) {

		try {
			RootUtils.excuteRootCMD("pm enable com.lbe.security");
			List<String> results = RootTools.sendShell(
					"pm enable com.lbe.security", 30000);
			System.out.println(results.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 备用方法

	public void runAsRoot(String[] cmds) throws Exception {
		Process p = Runtime.getRuntime().exec("su");
		DataOutputStream os = new DataOutputStream(p.getOutputStream());
		InputStream is = p.getInputStream();
		for (String tmpCmd : cmds) {
			os.writeBytes(tmpCmd + "\n");
			int readed = 0;
			byte[] buff = new byte[4096];

			// if cmd requires an output
			// due to the blocking behaviour of read(...)
			boolean cmdRequiresAnOutput = true;
			if (cmdRequiresAnOutput) {
				while (is.available() <= 0) {
					try {
						Thread.sleep(200);
					} catch (Exception ex) {
					}
				}

				while (is.available() > 0) {
					readed = is.read(buff);
					if (readed <= 0)
						break;
					String seg = new String(buff, 0, readed);
					// console.println("#> "+seg);
				}
			}
		}
		// os.writeBytes("exit\n");
		os.flush();
	}

	/**
	 * 执行 pm 命令 进行静默安装或者静默卸载
	 * 
	 * @param paramString
	 *            pm 指令语句
	 * @return 返回值为-1时 命令执行失败
	 */
	protected int excuteRootCMD(String paramString) {
		try {
			Process localProcess = Runtime.getRuntime().exec("su");
			Object localObject = localProcess.getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream(
					(OutputStream) localObject);
			localDataOutputStream
					.writeBytes((String) "export LD_LIBRARY_PATH=/vendor/lib:/system/lib\n");
			String str = String.valueOf(paramString);
			localObject = str + "\n";
			localDataOutputStream.writeBytes((String) localObject);
			localDataOutputStream.flush();
			localDataOutputStream.writeBytes("exit\n");
			localDataOutputStream.flush();
			localProcess.waitFor();
			int result = localProcess.exitValue();
			return (Integer) result;
		} catch (Exception localException) {
			localException.printStackTrace();
			return -1;
		}
	}
}
