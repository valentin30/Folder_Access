import { AiOutlineFile } from 'react-icons/ai'

export const File = ({ file }) => (
    <>
        <AiOutlineFile className='file' />
        <a href={`http://localhost:8085/${file.path}`}>{file.name}</a>
    </>
)
