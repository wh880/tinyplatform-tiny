/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.tinypc.test.plus;

import java.rmi.RemoteException;
import java.util.List;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.WorkCombiner;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class PlusWorkCombier implements WorkCombiner {

	public Warehouse combine(List<Warehouse> warehouseList)
			throws RemoteException {
		Warehouse w = new WarehouseDefault();
		int total = 0;
		for (Warehouse wsub : warehouseList) {
			Integer sub = wsub.get(PlusWork.RESULT);
			total += sub;
		}
		w.put(PlusWork.RESULT, total);
		return w;
	}
}
