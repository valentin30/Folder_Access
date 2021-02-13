import { HiFolder } from 'react-icons/hi'
import { Link } from 'react-router-dom'

export const Folder = ({ folder }) => (
    <>
        <HiFolder />
        <Link to={`/${folder.path}`}>{folder.name}</Link>
    </>
)
