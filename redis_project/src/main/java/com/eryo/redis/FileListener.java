package com.eryo.redis;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.MessageFormat;

/**
 * 监听文件的改变
 */
public class FileListener extends FileAlterationListenerAdaptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onFileChange(File file) {
        Main.lock.compareAndSet(false, true);
        //当文件改变后就重新加载
        Main.loadConfigJson();
        Main.lock.set(false);
    }
}
