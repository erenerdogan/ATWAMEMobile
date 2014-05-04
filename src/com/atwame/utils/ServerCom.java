package com.atwame.utils;

public class ServerCom {
	public static interface iErrorReportHandler{
		void reportErrorFromTask(String newErrorMsg);
	}
	public static interface iAsyncTerminatorCallback{
        void loadResponse(String event, Object response);
	}
    public static interface ErrorHandler{
        void handleError();
    }
}
