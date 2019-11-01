package com.dcits.sonicAutoTest.interfaceTest.createJsonFile;

import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.GetPreDataFromXml;
import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.SavePreDataToXml;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 生成格式为JSONArray的预言值
 */
public class GetPreDataToJsonArray {
    private static JSONArray preData_jsonArray;
    private static JSONObject preData_jsonObject;

    private volatile static GetPreDataToJsonArray getPreDataToJsonArray;

    private GetPreDataToJsonArray() {
    }

    public static GetPreDataToJsonArray getInstance() {
        if (null == getPreDataToJsonArray) {
            synchronized (GetPreDataToJsonArray.class) {
                if (null == getPreDataToJsonArray) {
                    getPreDataToJsonArray = new GetPreDataToJsonArray();
                }
            }
        }
        return getPreDataToJsonArray;
    }

    public static void main(String[] args) {
        String pre_filePath = SavePreDataToXml.preDataFilesPath + "normalJob" + ".xml";
        JSONArray preJSONArray = GetPreDataFromXml.getInstance().getPreDataFromXml(pre_filePath);
        JSONObject preData = preJSONArray.getJSONObject(0);
        GetPreDataToJsonArray.getInstance().getPreDataToJsonArray(preData,"normalJob");
    }
    /**
     * 生成JSONArray格式的预言值
     *
     * @param preDataJSON
     * @param pre_fileName
     */
    public void getPreDataToJsonArray(JSONObject preDataJSON, String pre_fileName) {
        preData_jsonArray = new JSONArray();
        preData_jsonObject = new JSONObject();
        //1.JOB列表
        JSONObject getList = preDataJSON.getJSONObject("getList");
        getPreData_getList(getList);
        //2.JOB定义详情
        JSONObject getChain = preDataJSON.getJSONObject("getChain");
        getPreData_getChain(getChain);
        //3.JOB指令，包括定时任务的恢复和停止操作
        JSONObject jobCommand = preDataJSON.getJSONObject("jobCommand");
        getPreData_jobCommand(jobCommand);
        //4.JOB运行列表
        JSONObject getCurrentRunJob = preDataJSON.getJSONObject("getCurrentRunJob");
        getPreData_getCurrentRunJob(getCurrentRunJob);
        //5.JOB运行详情（待定）
        JSONObject getRunChain = preDataJSON.getJSONObject("getRunChain");
        getPreData_getRunChain(getRunChain);
        //6.STEP运行列表,查看step运行信息,最好统计执行次数和个数
        JSONObject getCurrentRunStep = preDataJSON.getJSONObject("getCurrentRunStep");
        getPreData_getCurrentRunStep(getCurrentRunStep);
        //对step组和非step的信息整理和存储
        for (Object pre_key:preDataJSON.keySet()){
            String pre_keyString = pre_key.toString();
            if (pre_keyString.contains("step_")){
                preData_jsonObject.put(pre_keyString,preDataJSON.get(pre_keyString));
            }
        }
        preData_jsonArray.add(preData_jsonObject);
        SavePreDataToJsonFile.savePreDataToJsonFile(preData_jsonArray, pre_fileName);
    }

    //获取getRunChain
    private void getPreData_getRunChain(JSONObject getRunChain) {
        preData_jsonObject.put("getRunChain",getRunChain);
    }

    //获取 Job的Step依赖关系，将step信息整合为字符串，整合为多个step
    private void getPreData_getChain(JSONObject getChain) {
        String getChain_code = getChain.getString("code");
        String getChain_message = getChain.getString("message");
        JSONObject getChain_data = getChain.getJSONObject("data");
        JSONArray stepsInfo = getChain_data.getJSONArray("steps");
        String stepName = "";
        preData_jsonObject.put("getChain",getChain);
        for (int i = 0; i < stepsInfo.size(); i++) {
            JSONObject stepObject = stepsInfo.getJSONObject(i);
            stepName = stepObject.getString("stepName");
            if (stepObject != null){
                preData_jsonObject.put("getChainStep_"+stepName,stepObject);
            }
        }
    }

    //执行Job指令，获取的预言值
    private void getPreData_jobCommand(JSONObject jobCommand) {
        preData_jsonObject.put("jobCommand",jobCommand);
    }

    /**
     * JOB列表
     *
     * @param getList
     */
    public void getPreData_getList(JSONObject getList) {
        preData_jsonObject.put("getList", getList);
    }

    /**
     * JOB运行列表
     *
     * @param getCurrentRunJob
     */
    private void getPreData_getCurrentRunJob(JSONObject getCurrentRunJob) {
        preData_jsonObject.put("getCurrentRunJob",getCurrentRunJob);
    }

    /**
     * STEP运行列表
     *
     * @param getCurrentRunStep
     */
    private void getPreData_getCurrentRunStep(JSONObject getCurrentRunStep) {
        preData_jsonObject.put("getCurrentRunStep",getCurrentRunStep);
    }

    /**
     * STEP组信息
     *
     * @param getCurrentRunStepGroup
     */
    private void getPreData_getCurrentRunStepGroup(JSONObject getCurrentRunStepGroup) {
        String getCurrentRunStepGroup_totalCount = getCurrentRunStepGroup.getString("totalCount");
        preData_jsonObject.put("getCurrentRunStepGroup_totalCount", getCurrentRunStepGroup_totalCount);
    }

    /**
     * 分段运行信息
     *
     * @param getCurrentSplitRunStep
     */

    private void getPreData_getCurrentSplitRunStep(JSONObject getCurrentSplitRunStep) {
        String getCurrentSplitRunStep_totalCount = getCurrentSplitRunStep.getString("totalCount");
        preData_jsonObject.put("getCurrentSplitRunStep_totalCount", getCurrentSplitRunStep_totalCount);
    }


}