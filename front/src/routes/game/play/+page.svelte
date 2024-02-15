<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>
</svelte:head>

<script lang="ts">
    import { onMount } from 'svelte';
    import ace from 'ace-builds/src-noconflict/ace'; // 
    import 'ace-builds/src-noconflict/mode-python'; // 사용할 언어 모드
    import 'ace-builds/src-noconflict/theme-monokai'; // 사용할 테마
  
    let editor: any;
  
    onMount(() => {
      editor = ace.edit('editor'); // 'editor'는 에디터를 적용할 HTML 엘리먼트의 ID입니다.
      editor.setTheme('ace/theme/monokai');
      editor.session.setMode('ace/mode/python');
    });

    async function runPythonCode() {
        // @ts-ignore
        const runner = new BrythonRunner({
            stdout: {
                write(content: string) {
                    console.log('StdOut: ' + content);
                },
                flush() {},
            },
            stderr: {
                write(content: string) {
                    console.error('StdErr: ' + content);
                },
                flush() {},
            },
            stdin: {
                async readline() {
                    var userInput = prompt();
                    console.log('Received StdIn: ' + userInput);
                    return userInput;
                },
            }
        });
        console.log('Run Code:');
        //await runner.runCode('print("hello world")\nprint("from Brython Runner")');
        const userCodeIndented = editor.getValue().split('\n').map((line: String) => `        ${line}`).join('\n');
        const finalCode = `${gamePre}\n${userCodeIndented}\n${gamePost}`;

        console.log(finalCode);

        await runner.runCode(finalCode);
        console.log('Done.');
		}

        var gamePre = 
        `
        class Character:
            def __init__(self, tile_map):
                self.tile_map = tile_map
                self.position = [0.0, 0.0]  # Character's initial position as floats
                self.frames = []
        
            def can_move(self, x, y):
                # Check if within map bounds
                if x < 0 or y < 0 or y >= len(self.tile_map) or x >= len(self.tile_map[0]):
                    return False
                # Check if tile is passable
                return self.tile_map[y][x] == 0
        
            def move(self, dx, dy):
                new_x = self.position[0] + dx
                new_y = self.position[1] + dy
                # Round to first decimal
                new_x_rounded = round(new_x, 1)
                new_y_rounded = round(new_y, 1)
        
                # Check if can move to new position
                if self.can_move(int(new_x_rounded), int(new_y_rounded)):
                    self.position = [new_x, new_y]
                else:  # Cannot move, stay in current position
                    new_x = self.position[0]
                    new_y = self.position[1]
        
                self.frames.append((round(new_x, 1), round(new_y, 1)))
        
            def moveLeft(self):
                for _ in range(10):  # Generate 10 frames
                    self.move(-0.1, 0)
        
            def moveRight(self):
                for _ in range(10):
                    self.move(0.1, 0)
        
            def moveUp(self):
                for _ in range(10):
                    self.move(0, 0.1)
        
            def moveDown(self):
                for _ in range(10):
                    self.move(0, -0.1)
        
            def get_frames(self):
                return self.frames
        
        
        # Example Tile Map: 0 = passable, 1 = impassable
        tile_map = [
            [0, 0, 1, 0],
            [0, 1, 0, 0],
            [0, 0, 0, 1],
            [0, 0, 0, 0]
        ]
        
        # Create a character and move it around the map
        hero = Character(tile_map)
        # hero.moveRight()
        # hero.moveUp()
        # hero.moveUp()
        # hero.moveUp()
        `;
            
            var gamePost = 
        `
        # Print the list of frames with character's positions
        for frame in hero.get_frames():
            print(frame)
        `;

        let explanation: string = '#로켓의 재료를 향해 가세요.\n#폭탄을 피하세요!\n#아래에 코드를 입력하고 완료되면 실행을 클릭합니다.\n';
</script>
  
 
	
    

<div class="flex flex-col items-center justify-center p-8 h-[90vh]">
    <div class="border-2 border-black w-full h-full flex flex-row">
        <div class="border-2 border-black w-2/3 relative">
            <div class="absolute border-2 border-black w-fit top-[2%] left-[1%]">뒤로가기</div>
            <div class="avatar top-[10%] left-[1%]">
                <div class="w-16 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
                </div>
            </div>
            <div class="flex flex-row absolute top-[20%] left-[1%] gap-2">
                <div class="border-2 h-fit">8</div>
                <div>
                    <div>홍길동</div>
                    <div>체력바</div>
                </div>
            </div>
            <div class="w-[10vw] h-[8vw] border-2 border-black flex justify-center items-center absolute right-0 top-4">
                미션정보
            </div>
        </div>
        <div class="border-2 border-black w-1/3 relative">
            <div id="editor" style="height: 500px; width: 100%;">{explanation}</div>
            <button class="btn btn-outline" on:click={runPythonCode}>Run Python Code</button>
            <div class="flex justify-center items-center">
                <div class="border-2 border-black w-[16vw] h-[8vw] flex justify-center items-center">
                    코드힌트
                </div>
            </div>
        </div>
    </div>
</div>
