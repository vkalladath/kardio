/*******************************************************************************
 * Copyright 2019 T-Mobile, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/**
 * 
 */
package com.tmobile.kardio.surveiller.counters;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tmobile.kardio.surveiller.apidashboard.APIDashboardTask;
import com.tmobile.kardio.surveiller.util.DBQueryUtil;
import com.tmobile.kardio.surveiller.util.MailSenderUtil;
import com.tmobile.kardio.surveiller.vo.CounterDetailVO;
import com.tmobile.kardio.test.TestDaoService;

import mockit.Mock;
import mockit.MockUp;

/**
 * @author U29842
 *
 */
public class CounterDataLoaderTest {

	TestDaoService daoService = new TestDaoService();
	
	@Test
	public void testDoDataLoad() throws Exception{
		//EnvCounterEntity ece = daoService.createEnvCounterEntity();
		//CounterDataLoader.doDataLoad();
		
		/*Mocking the getResponse, Since its calling prometheous in the original system*/
		new MockUp<DBQueryUtil>() {
			@Mock
			public List<CounterDetailVO> getCounterDetails() {
				CounterDetailVO counterDetails = new CounterDetailVO();
				counterDetails.setEnvironmentCounterId(6);
				counterDetails.setEnvironmentId(1);
				counterDetails.setCounterId(1);
				counterDetails.setCounterMetricTypeId(1);
				counterDetails.setCounterMetricTypeClassName("com.tmobile.gdm.surveiller.counters.PercentageUpTimeHandler");
				counterDetails.setParameter1("4,6");
				counterDetails.setParameter2("AVG");
				List<CounterDetailVO> counterList = new ArrayList<CounterDetailVO>();
				counterList.add(counterDetails);
				return counterList;
			}
		};
		
		new MockUp<MailSenderUtil>(){
			
			@Mock
			public void sendMail(String messageText, String toMail, String subject){
				
			}
			
		};
		
		CounterDataLoader.doDataLoad();
	}
	
	@Test
	public void testPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
	  Constructor<CounterDataLoader> constructor = CounterDataLoader.class.getDeclaredConstructor();
	  assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	  constructor.setAccessible(true);
	  constructor.newInstance();
	}
	
}
