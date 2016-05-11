package org.tinygroup.mmseg4j;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class NewMMSegTokenizer extends Tokenizer{

	public NewMMSegTokenizer(Seg seg, Reader input) {
		super(input);
		mmSeg = new MMSeg(input, seg);

		termAtt = addAttribute(CharTermAttribute.class);
		offsetAtt = addAttribute(OffsetAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
	}
	
	private MMSeg mmSeg;

	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;

	public void reset() throws IOException {
		super.reset();
		mmSeg.reset(input);
	}

	//lucene 2.9/3.0
	@Override
	public final boolean incrementToken() throws IOException {
		clearAttributes();
		Word word = mmSeg.next();
		if(word != null) {
			//lucene 3.0
			//termAtt.setTermBuffer(word.getSen(), word.getWordOffset(), word.getLength());
			//lucene 3.1
			termAtt.copyBuffer(word.getSen(), word.getWordOffset(), word.getLength());
			offsetAtt.setOffset(word.getStartOffset(), word.getEndOffset());
			typeAtt.setType(word.getType());
			return true;
		} else {
			end();
			return false;
		}
	}

}
