export async function runPythonCode(editor: any) {
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
    const userCodeIndented = editor.getValue().split('\n').map((line: String) => `    ${line}`).join('\n');
    const finalCode = `${gamePre}\n${userCodeIndented}\n${gamePost}`; // TODO REMOVE THIS

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
