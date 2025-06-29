package com.mhua.adminbackend.handler;

import org.flowable.bpmn.model.BaseElement;
import org.flowable.engine.impl.bpmn.parser.BpmnParse;
import org.flowable.engine.impl.bpmn.parser.handler.ProcessParseHandler;

public class CustomBpmnParseHandler extends ProcessParseHandler {

    private final String customName;

    public CustomBpmnParseHandler(String customName) {
        this.customName = customName;
    }

    @Override
    public void parse(BpmnParse bpmnParse, BaseElement element) {
        super.parse(bpmnParse, element);
        bpmnParse.getCurrentProcess().setName(customName);
    }
}