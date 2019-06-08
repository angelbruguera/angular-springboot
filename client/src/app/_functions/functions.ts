/******* Aquestes funcions s'utilitzaràn en diversos components *******/

/******* Retornarà una posició menys d'una array *******/
export const setLeft = (index, arrayLength) => {
    if (index <= 0) {
        return index = arrayLength - 1;
    }

    return index -= 1;
};


/******* Retornarà una posició més d'una array *******/
export const setRight = (index, arrayLength) => {
    if (index >= arrayLength - 1) {

        return index = 0;
    }

    return index += 1;
};
