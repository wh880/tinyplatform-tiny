package org.tinygroup.sequence.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.tinygroup.sequence.SequenceDao;
import org.tinygroup.sequence.SequenceRange;
import org.tinygroup.sequence.Sequence;
import org.tinygroup.sequence.exception.SequenceException;

/**
 * 默认的序列号生成接口实现
 * @author renhui
 *
 */
public class DefaultSequence implements Sequence {

	private final Lock lock = new ReentrantLock();

	private SequenceDao sequenceDao;

	/**
	 * 序列名称
	 */
	private String name;

	private volatile SequenceRange currentRange;

	public long nextValue() throws SequenceException {
		if (currentRange == null) {
			lock.lock();
			try {
				if (currentRange == null) {
					currentRange = sequenceDao.nextRange(name);
				}
			} finally {
				lock.unlock();
			}
		}

		long value = currentRange.getAndIncrement();
		if (value == -1) {
			lock.lock();
			try {
				for (;;) {
					if (currentRange.isOver()) {
						currentRange = sequenceDao.nextRange(name);
					}

					value = currentRange.getAndIncrement();
					if (value == -1) {
						continue;
					}

					break;
				}
			} finally {
				lock.unlock();
			}
		}

		if (value < 0) {
			throw new SequenceException("Sequence value overflow, value = {0}",value);
		}

		return value;
	}

	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
