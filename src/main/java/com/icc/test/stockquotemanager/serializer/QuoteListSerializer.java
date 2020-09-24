package com.icc.test.stockquotemanager.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.icc.test.stockquotemanager.entity.Quote;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class QuoteListSerializer extends StdSerializer<List<Quote>> {

    static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public QuoteListSerializer(Class<List<Quote>> t) {
        super(t);
    }


    @Override
    public void serialize(List<Quote> quotes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        for (Quote quote : quotes) {
            jsonGenerator.writeStringField(dateFormat.format(quote.getDate()), quote.getQuotation().toString());
        }

        jsonGenerator.writeEndObject();
    }
}
