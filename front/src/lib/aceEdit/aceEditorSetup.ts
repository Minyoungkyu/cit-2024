import ace from 'ace-builds/src-noconflict/ace'; // 
import 'ace-builds/src-noconflict/mode-python'; // 사용할 언어 모드
import 'ace-builds/src-noconflict/theme-monokai'; // 사용할 테마
import 'ace-builds/src-noconflict/ext-language_tools';

var Range = ace.require('ace/range').Range;

export function setupAceEditor(editorId: string, customCompletions: any[]) {
    const editor = ace.edit(editorId);
    editor.setTheme('ace/theme/monokai');
    editor.session.setMode('ace/mode/python');
    editor.setFontSize("30px");
    editor.setHighlightActiveLine(false);
    editor.setOptions({
        enableBasicAutocompletion: false,
        enableLiveAutocompletion: true,
        wrap:true,
        hasCssTransforms: true
    });

    const langTools = ace.require("ace/ext/language_tools");

    const customCompleter = {
        getCompletions: function(editor: any, session: any, pos: any, prefix: any, callback: any) {
            callback(null, customCompletions);
        }
    };

    langTools.setCompleters([customCompleter]);

    return editor;
}

