package org.tinygroup.fileresolver;

/**
 * 扫描或者动态刷新时，文件发生变化后的行为处理的Lisenter
 * 
 * @author chenjiao
 * 
 */
public interface ChangeLisenter {
	void change(FileResolver resolver);
}
