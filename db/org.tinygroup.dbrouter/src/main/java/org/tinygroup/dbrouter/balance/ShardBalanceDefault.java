/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.dbrouter.balance;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.config.Shard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 默认分区负载均衡实现类
 */
public class ShardBalanceDefault implements ShardBalance {
    Random random = new Random(System.currentTimeMillis());

    public List<Shard> getWritableShard(Partition partition) {
        List<Shard> writableShards = new ArrayList<Shard>();
        for (Shard shard : partition.getWritableShardList()) {
            if (shard.isWriteAble()) {
                writableShards.add(shard);
            }
        }
        if (writableShards.size() == 0) {
            throw new RuntimeException("No suitable shard exist.");
        }
        return writableShards;
    }

    public Shard getReadableShard(Partition partition) {
        int allWeight = 0;
        Shard selectedShard = partition.getReadShardList().get(0);
        for (Shard shard : partition.getReadShardList()) {
            allWeight += shard.getReadWeight();
        }
        int weightValue = (randomInt() % allWeight);
        for (Shard shard : partition.getReadShardList()) {
            weightValue -= shard.getReadWeight();
            if (weightValue < 0) {
                return shard;
            }
        }
        return selectedShard;
    }

    private int randomInt() {
        int value = random.nextInt();
        if (value < 1) {
            value = -value;
        }
        return value;
    }

	public Shard getReadShardWithTransaction(Partition partition) {
		List<Shard> writeShard = partition.getWritableShardList();
		if (!CollectionUtil.isEmpty(writeShard)) {
			return writeShard.get(randomInt()% writeShard.size());
		}else{
			return getReadableShard(partition);
		}
	}
}
