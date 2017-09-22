// Generated from Poly.g4 by ANTLR 4.4

package poly;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PolyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VAR=1, MUL=2, POW=3, PLUS=4, MIN=5, NUM=6, WHITESPACE=7;
	public static final String[] tokenNames = {
		"<INVALID>", "VAR", "'*'", "'^'", "'+'", "'-'", "NUM", "WHITESPACE"
	};
	public static final int
		RULE_poly = 0, RULE_sumterms = 1, RULE_term = 2;
	public static final String[] ruleNames = {
		"poly", "sumterms", "term"
	};

	@Override
	public String getGrammarFileName() { return "Poly.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    // This method makes the lexer or parser stop running if it encounters
	    // invalid input and throw a RuntimeException.
	    public void reportErrorsAsExceptions() {
	        //removeErrorListeners();
	        
	        addErrorListener(new ExceptionThrowingErrorListener());
	    }
	    
	    private static class ExceptionThrowingErrorListener 
	                                              extends BaseErrorListener {
	        @Override
	        public void syntaxError(Recognizer<?, ?> recognizer,
	                Object offendingSymbol, int line, int charPositionInLine,
	                String msg, RecognitionException e) {
	            throw new RuntimeException(msg);
	        }
	    }

	public PolyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PolyContext extends ParserRuleContext {
		public SumtermsContext sumterms() {
			return getRuleContext(SumtermsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PolyParser.EOF, 0); }
		public PolyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_poly; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).enterPoly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).exitPoly(this);
		}
	}

	public final PolyContext poly() throws RecognitionException {
		PolyContext _localctx = new PolyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_poly);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6); sumterms(0);
			setState(7); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SumtermsContext extends ParserRuleContext {
		public TerminalNode MIN() { return getToken(PolyParser.MIN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public SumtermsContext sumterms() {
			return getRuleContext(SumtermsContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(PolyParser.PLUS, 0); }
		public SumtermsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sumterms; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).enterSumterms(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).exitSumterms(this);
		}
	}

	public final SumtermsContext sumterms() throws RecognitionException {
		return sumterms(0);
	}

	private SumtermsContext sumterms(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SumtermsContext _localctx = new SumtermsContext(_ctx, _parentState);
		SumtermsContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_sumterms, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(10); term();
			}
			_ctx.stop = _input.LT(-1);
			setState(20);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(18);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						_localctx = new SumtermsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_sumterms);
						setState(12);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(13); match(PLUS);
						setState(14); term();
						}
						break;
					case 2:
						{
						_localctx = new SumtermsContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_sumterms);
						setState(15);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(16); match(MIN);
						setState(17); term();
						}
						break;
					}
					} 
				}
				setState(22);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(PolyParser.VAR, 0); }
		public List<TerminalNode> NUM() { return getTokens(PolyParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(PolyParser.NUM, i);
		}
		public TerminalNode POW() { return getToken(PolyParser.POW, 0); }
		public TerminalNode MUL() { return getToken(PolyParser.MUL, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PolyListener ) ((PolyListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_term);
		try {
			setState(36);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(23); match(NUM);
				setState(24); match(MUL);
				setState(25); match(VAR);
				setState(26); match(POW);
				setState(27); match(NUM);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(28); match(NUM);
				setState(29); match(MUL);
				setState(30); match(VAR);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(31); match(VAR);
				setState(32); match(POW);
				setState(33); match(NUM);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(34); match(VAR);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(35); match(NUM);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1: return sumterms_sempred((SumtermsContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean sumterms_sempred(SumtermsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 3);
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t)\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\25\n\3"+
		"\f\3\16\3\30\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\5\4\'\n\4\3\4\2\3\4\5\2\4\6\2\2+\2\b\3\2\2\2\4\13\3\2\2\2\6&\3\2\2\2"+
		"\b\t\5\4\3\2\t\n\7\2\2\3\n\3\3\2\2\2\13\f\b\3\1\2\f\r\5\6\4\2\r\26\3\2"+
		"\2\2\16\17\f\5\2\2\17\20\7\6\2\2\20\25\5\6\4\2\21\22\f\4\2\2\22\23\7\7"+
		"\2\2\23\25\5\6\4\2\24\16\3\2\2\2\24\21\3\2\2\2\25\30\3\2\2\2\26\24\3\2"+
		"\2\2\26\27\3\2\2\2\27\5\3\2\2\2\30\26\3\2\2\2\31\32\7\b\2\2\32\33\7\4"+
		"\2\2\33\34\7\3\2\2\34\35\7\5\2\2\35\'\7\b\2\2\36\37\7\b\2\2\37 \7\4\2"+
		"\2 \'\7\3\2\2!\"\7\3\2\2\"#\7\5\2\2#\'\7\b\2\2$\'\7\3\2\2%\'\7\b\2\2&"+
		"\31\3\2\2\2&\36\3\2\2\2&!\3\2\2\2&$\3\2\2\2&%\3\2\2\2\'\7\3\2\2\2\5\24"+
		"\26&";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}