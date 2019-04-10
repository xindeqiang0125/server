package com.xcs.server.opc.memory;

import java.util.Set;

public interface RequiredDataSubscriber extends DataSubscriber {
    Set<String> getRequiredPoints();
}
