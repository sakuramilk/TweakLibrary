/*
 * Copyright (C) 2011-2012 sakuramilk <c.sakuramilk@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sakuramilk.util;

import java.io.File;

import android.util.Log;

public class SystemCommand {

    private static final String TAG = "SystemCommand";

    public static void reboot(String action) {
        Log.d(TAG, "execute reboot action=" + action);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }

        process.write("sync\n");
        process.write("sync\n");
        process.write("sync\n");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        if ("recovery".equals(action) || "download".equals(action)) {
            process.write("reboot " + action + "\n");
        } else {
            process.write("reboot\n");
        }

        process.term();
    }

    public static void install_zip(String targetZip) {
        Log.d(TAG, "execute install_zip target=" + targetZip);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("echo \"install_zip(\\\"" + targetZip + "\\\");\" > /cache/recovery/extendedcommand\n");
        process.term();
    }

    public static void backup_rom(String targetDir) {
        Log.d(TAG, "execute backup_rom target=" + targetDir);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("echo \"ui_print(\\\"Tweak Application ROM Manager\\\");\" > /cache/recovery/extendedcommand\n");
        process.write("echo \"ui_print(\\\"`date`\\\");\" >> /cache/recovery/extendedcommand\n");
        process.write("echo \"assert(backup_rom(\\\"" + targetDir + "\\\"));\" >> /cache/recovery/extendedcommand\n");
        process.term();
    }

    public static void restore_rom(String targetDir) {
        Log.d(TAG, "execute restore_rom target=" + targetDir);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("echo \"restore_rom(\\\"" + targetDir + "\\\");\" > /cache/recovery/extendedcommand\n");
        process.term();
    }

    public static void copy(String src, String dst) {
        Log.d(TAG, "execute copy src=" + src + " dst=" + dst);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("cp " + src + " " + dst + "\n");
        process.term();
    }

    public static void move(String src, String dst) {
        Log.d(TAG, "execute move src=" + src + " dst=" + dst);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("mv " + src + " " + dst + "\n");
        process.term();
    }
    
    public static void rm(String path) {
        Log.d(TAG, "execute rm path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("rm " + path + "\n");
        process.term();
    }
    
    public static void mkdir(String path) {
        Log.d(TAG, "execute mkdir path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("mkdir -p " + path + "\n");
        process.term();
    }

    public static void touch(String path, String permission) {
        Log.d(TAG, "execute touch path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("touch " + path + "\n");
        process.write("chmod " + permission + " " + path + "\n");
        process.term();
    }
    
    public static void chmod(String path, String permission) {
        Log.d(TAG, "execute chmod path=" + path + " permission=" + permission);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("chmod " + permission + " " + path + "\n");
        process.term();
    }

    public static void start_dock() {
        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("export LD_LIBRARY_PATH=/vendor/lib:/system/lib\n");
        process.write("am broadcast -a android.intent.action.DOCK_EVENT --ei android.intent.extra.DOCK_STATE 1\n");
        process.term();
    }

    public static void stop_dock() {
        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("export LD_LIBRARY_PATH=/vendor/lib:/system/lib\n");
        process.write("am broadcast -a android.intent.action.DOCK_EVENT --ei android.intent.extra.DOCK_STATE 0\n");
        process.term();
    }

    public static void gsm_network_tweak() {
        Log.d(TAG, "execute gsm_network_tweak");

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("echo 0 > /proc/sys/net/ipv4/tcp_ecn\n");
        process.write("echo 1 > /proc/sys/net/ipv4/route/flush\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_rfc1337\n");
        process.write("echo 0 > /proc/sys/net/ipv4/ip_no_pmtu_disc\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_sack\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_fack\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_window_scaling\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_timestamps\n");
        process.write("echo 4096 39000 187000 > /proc/sys/net/ipv4/tcp_rmem\n");
        process.write("echo 4096 39000 187000 > /proc/sys/net/ipv4/tcp_wmem\n");
        process.write("echo 187000 187000 187000 > /proc/sys/net/ipv4/tcp_mem\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_no_metrics_save\n");
        process.write("echo 1 > /proc/sys/net/ipv4/tcp_moderate_rcvbuf\n");
        process.term();
    }

    public static void mount(String device, String mountPoint, String format, String option) {
        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        String command = "mount ";
        if (!Misc.isNullOfEmpty(format)) {
            command += "-t " + format + " ";
        }
        if (!Misc.isNullOfEmpty(option)) {
            command += "-o " + option + " ";
        }
        command += device + " " + mountPoint + "\n";
        process.write(command);
        process.term();
    }

    public static void umount(String mountPoint) {
        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("umount " + mountPoint + "\n");
        process.term();
    }

    public static boolean check_mount(String path) {
    	Log.d(TAG, "execute check_mount path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return false;
        }
        String mount_list[] = RuntimeExec.execute("mount\n", true);
        for (String mount : mount_list) {
        	if (mount.indexOf(path) >= 0) {
        		return true;
        	}
        }
    	return false;
    }

    public static void remount_system_rw() {
        Log.d(TAG, "execute remount_system_rw");

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("mount -o rw,remount /system\n");
        process.term();
    }

    public static void remount_system_ro() {
        Log.d(TAG, "execute remount_system_ro");

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("mount -o ro,remount /system\n");
        process.term();
    }

    public static String uname(String option) {
        String[] ret = RuntimeExec.execute("uname " + option + "\n", true);
        return ret[0];
    }

    public static String get_prop(String key, String defaultValue) {
        Log.d(TAG, "execute set_prop key=" + key + " value=" + defaultValue);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return defaultValue;
        }
        process.write("getprop " + key + "\n");
        String[] ret = process.read();
        process.term();
        if (ret != null && ret.length > 0) {
            return ret[0];
        }
        return defaultValue;
    }

    public static String get_prop(String key) {
        return get_prop(key, null);
    }

    public static void set_prop(String key, String value) {
        Log.d(TAG, "execute set_prop key=" + key + " value=" + value);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("setprop " + key + " " + value + "\n");
        process.term();
    }

    public static void make_ext4_image(String path, int blockSize, int blockCount) {
        Log.d(TAG, "execute make_ext4_image path=" + path +
                " blockSize=" + blockSize+ " blockCount=" + blockCount);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("dd if=/dev/zero of=" + path +
                " bs=" + blockSize + " count=" + blockCount + "\n");
        process.write("mke2fs -T ext4 -F " + path + "\n");
        process.term();
    }

    public static void sbl_backup_for_gs2(String path) {
        Log.d(TAG, "execute sbl_backup_for_gs2 path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("dd if=/dev/block/mmcblk0p2 of=" + path + " bs=4096\n");
        File file = new File(path);
        String dir = file.getParent();
        process.write("cd " + dir + "\n");
        process.write("md5sum " + file.getName() + " > " + file.getName() + ".md5\n");
        process.term();
    }

    public static void efs_backup_for_gs2(String path) {
        Log.d(TAG, "execute efs_backup_for_gs2 path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }
        process.write("dd if=/dev/block/mmcblk0p1 of=" + path + " bs=4096\n");
        File file = new File(path);
        String dir = file.getParent();
        process.write("cd " + dir + "\n");
        process.write("md5sum " + file.getName() + " > " + file.getName() + ".md5\n");
        process.term();
    }

    public static void partition_backup_for_gs3(String path) {
        Log.d(TAG, "execute partition_backup_for_gs3 path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }

        process.write("mkdir -p " + path + "\n");
        process.write("busybox dd if=/dev/block/mmcblk0p1 of=" + path + "/modem.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p2 of=" + path + "/sbl1.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p3 of=" + path + "/sbl2.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p4 of=" + path + "/sbl3.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p5 of=" + path + "/aboot.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p6 of=" + path + "/rpm.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p8 of=" + path + "/tz.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p9 of=" + path + "/pad.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p10 of=" + path + "/param.img bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p11 of=" + path + "/efs.img bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p12 of=" + path + "/modemst1.img bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p13 of=" + path + "/modemst2.img bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p16 of=" + path + "/persist.img.ext4 bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p19 of=" + path + "/fota.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p20 of=" + path + "/backup.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p21 of=" + path + "/fgs.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p22 of=" + path + "/ssd.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p23 of=" + path + "/grow.bin bs=4096\n");

        process.write("cd " + path + "\n");
        process.write("md5sum * > backup.md5\n");
        process.term();
    }

    public static void partition_backup_for_gnt(String path) {
        Log.d(TAG, "execute partition_backup_for_gs3 path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }

        process.write("mkdir -p " + path + "\n");
        process.write("busybox dd if=/dev/block/mmcblk0p1 of=" + path + "/smd_hdr.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p2 of=" + path + "/sbl1.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p3 of=" + path + "/sbl2.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p5 of=" + path + "/rpm.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p6 of=" + path + "/sbl3.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p7 of=" + path + "/aboot.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p9 of=" + path + "/tz.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p10 of=" + path + "/ssd.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p11 of=" + path + "/pit.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p12 of=" + path + "/param.lfs bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p14 of=" + path + "/efs.img bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p15 of=" + path + "/msm_st2.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p16 of=" + path + "/msm_fsg.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p18 of=" + path + "/efsclear1.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p19 of=" + path + "/efsclear2.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p20 of=" + path + "/m9k_fsg.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p21 of=" + path + "/enc.img.ext4 bs=4096\n");

        process.write("cd " + path + "\n");
        process.write("md5sum * > backup.md5\n");
        process.term();
    }

    public static void partition_backup_for_gs4(String path) {
        Log.d(TAG, "execute partition_backup_for_gs4 path=" + path);

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }

        process.write("mkdir -p " + path + "\n");

        process.write("busybox dd if=/dev/block/mmcblk0p1 of=" + path + "/apnhlos.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p2 of=" + path + "/mdm.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p3 of=" + path + "/sbl1.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p4 of=" + path + "/sbl2.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p5 of=" + path + "/sbl3.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p6 of=" + path + "/aboot.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p7 of=" + path + "/rpm.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p8 of=" + path + "/tz.mbn bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p9 of=" + path + "/pad.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p10 of=" + path + "/efs.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p11 of=" + path + "/modemst1.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p12 of=" + path + "/modemst2.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p13 of=" + path + "/m9kefs1.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p14 of=" + path + "/m9kefs2.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p15 of=" + path + "/m9kefs3.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p17 of=" + path + "/persist.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p19 of=" + path + "/param.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p22 of=" + path + "/fota.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p23 of=" + path + "/backup.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p24 of=" + path + "/fsg.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p25 of=" + path + "/ssd.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p26 of=" + path + "/persdata.bin bs=4096\n");
        process.write("busybox dd if=/dev/block/mmcblk0p28 of=" + path + "/carrier.bin bs=4096\n");

        process.write("cd " + path + "\n");
        process.write("md5sum * > backup.md5\n");
        process.term();
    }

    public static void time_adjust_recovery() {
        Log.d(TAG, "execute time_adjust_recovery");

        RootProcess process = new RootProcess();
        if (!process.init()) {
            return;
        }

        String backup_path = Misc.getSdcardPath(true, true) + Constant.CWM_DIR;
        process.write("mkdir -p " + backup_path + "\n");
        process.write("date +%s > " + backup_path + "/.date.now\n");
        process.term();

        reboot("recovery");
    }

    public static String df(String device) {
        String ret[] = RuntimeExec.execute("df " + device + " | grep " + device + "\n", true);
        return ret[0];
    }

    public static String get_felica_key() {
        String ret[] = RuntimeExec.execute("cat /proc/cmdline\n", true);
        String[] cmdline = ret[0].split(" ");
        for (String item : cmdline) {
        	if (item.startsWith("cordon")) {
        		String[] cordon = item.split("=");
        		if (cordon.length == 2) {
        			return cordon[1];
        		}
        	}
        }
        return null;
    }
}
