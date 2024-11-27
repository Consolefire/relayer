package ct.com.consolefire.relayer.core.reader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.consolefire.relayer.core.reader.MessageSource;
import com.consolefire.relayer.core.reader.MessageSourceReader;
import com.consolefire.relayer.model.outbox.OutboundMessage;
import java.util.Collection;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class MessageSourceReaderTest {

    private DataSource dataSource;

    private MessageSource messageSource;
    private MessageSourceReader<UUID, OutboundMessage<UUID>> messageSourceReader;


    void setup() {
        String sql = "select * from outbound_messages where state = 'NEW'";

        messageSourceReader = new TestMessageSourceReader();
    }

    @Test
    void shouldReadMessagesFromSource() {
        Collection<OutboundMessage<UUID>> messages = messageSourceReader.read(messageSource);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

}
