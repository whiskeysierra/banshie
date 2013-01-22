package org.whiskeysierra.banshie.execution.event;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @Type(value = CpuTimeEvent.class, name = "cpu"),
    @Type(value = MemoryUsageEvent.class, name = "memory")
})
public interface Event {

    long getTime();

    void setTime(long time);

    long getValue();

    void setValue(long value);

}
